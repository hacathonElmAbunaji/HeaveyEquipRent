package com.hacathon.heavyequipmentrent.ui;

import static com.hacathon.heavyequipmentrent.Constants.Constants.IS_FIRSTRUN;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.database.UserLoginCredBean;
import com.hacathon.heavyequipmentrent.models.Requests.LoginRequest;
import com.hacathon.heavyequipmentrent.models.Responses.LoginResponse;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    View view;
    MainCallBacks mainCallBacks;
    Button button_login;
    ImageView imageView_language;
    TextInputLayout textInputLayout_username, textInputLayout_password;
    TextInputEditText editTextUsername, editTextPassword;
    CheckBox chk_box_remember_me;
    boolean rememberMyLoginCred = false;

    public LoginFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static LoginFragment newInstance(MainCallBacks mainCallBacks) {
        LoginFragment fragment = new LoginFragment(mainCallBacks);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }//OnCreate

    @Override
    public void onResume() {
        super.onResume();
        fillRememberMeFromRealm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.HIDE);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.HIDE);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.login_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);


        initLayouts();
        onClickListeners();


        return view;
    }//OnCreateView


    public void initLayouts(){

        button_login = view.findViewById(R.id.button_login);
        imageView_language = view.findViewById(R.id.imageView_language);
        textInputLayout_username = view.findViewById(R.id.textInputLayout_username);
        textInputLayout_password = view.findViewById(R.id.textInputLayout_password);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        chk_box_remember_me = view.findViewById(R.id.chk_box_remember_me);

    }

    public void onClickListeners(){
        imageView_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage();
            }
        });

//        editTextUsername.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "Username clicked", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        editTextPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "Password clicked", Toast.LENGTH_LONG).show();
//            }
//        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidToSubmit()){
                    SubmitLogin();
                }
            }
        });

        chk_box_remember_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rememberMyLoginCred = chk_box_remember_me.isChecked();
            }
        });

    }

    //username : renter , password : Welcome1!
    private void SubmitLogin(){

        if (NetworkHelper.getInstance().isConnected()){

            LoginRequest req = new LoginRequest();
            req.setUsername(editTextUsername.getText().toString());
            req.setPassword(editTextPassword.getText().toString());

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<LoginResponse> cResponse = MyApplication.getRestClient().getApiService().loginService(req);
            cResponse.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final LoginResponse res = response.body();
                        if (res.getCode() != null && res.getCode() == 0){
                            createUserProfileAndSave(req.getUsername(), req.getPassword(), res);
                        }
                        else {
                            MyApplication.getInstance().reportError(getString(R.string.error_happened));
                        }
                    }
                    else {
                        MyApplication.getInstance().reportError(getString(R.string.error_serverconn));
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }

    private void createUserProfileAndSave(String username, String password, LoginResponse response){
        Realm.getDefaultInstance().beginTransaction();
        UserBean userBean = new UserBean();
        userBean.setUserID(response.getUserId());
        userBean.setDisplayName(response.getDisplayName());
        userBean.setToken(response.getToken());
        Realm.getDefaultInstance().copyToRealmOrUpdate(userBean);
        Realm.getDefaultInstance().commitTransaction();


        if (rememberMyLoginCred){
            createRememberMeUserCred(username, password);
        }else {
            clearRememberMeFromRealm();
        }


        ((MainActivity) getActivity()).clearBackStack();
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Home);
    }

    private void createRememberMeUserCred(String username, String password){
        Realm.getDefaultInstance().beginTransaction();
        UserLoginCredBean userLoginCredBean = new UserLoginCredBean();
        userLoginCredBean.setUsername(username);
        userLoginCredBean.setPassword(password);
        Realm.getDefaultInstance().copyToRealmOrUpdate(userLoginCredBean);
        Realm.getDefaultInstance().commitTransaction();
    }

    private void fillRememberMeFromRealm(){
        UserLoginCredBean userLoginCredBean = Realm.getDefaultInstance().where(UserLoginCredBean.class).findFirst();
        if (userLoginCredBean != null && userLoginCredBean.isValid()){
            editTextUsername.setText(userLoginCredBean.getUsername());
            editTextPassword.setText(userLoginCredBean.getPassword());
        }
    }

    private void clearRememberMeFromRealm(){
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().where(UserLoginCredBean.class).findAll().deleteAllFromRealm();
        Realm.getDefaultInstance().commitTransaction();
    }

    private boolean isValidToSubmit(){

        textInputLayout_username.setErrorEnabled(false);
        textInputLayout_password.setErrorEnabled(false);

        if (editTextUsername.getText().toString().isEmpty()){
            textInputLayout_username.setError(getString(R.string.login_valid_username_error_message));
            return false;
        }

        if (editTextUsername.getText().toString().length() < 3){
            textInputLayout_username.setError(getString(R.string.login_valid_username_length_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().isEmpty()){
            textInputLayout_password.setError(getString(R.string.login_valid_password_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().length() < 3){
            textInputLayout_password.setError(getString(R.string.login_valid_password_length_error_message));
            return false;
        }

        return true;
    }



    private void changeLanguage(){
        if(LanguageManager.isCurrentLangARabic()){
            LanguageManager.setCurrentLocalLanguage(getActivity().getBaseContext(), "en");
            SharedPreferences pref = MyApplication.getSharedPref(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(IS_FIRSTRUN, false);
            editor.apply();

            Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else {
            LanguageManager.setCurrentLocalLanguage(getActivity().getBaseContext(), "ar");
            SharedPreferences pref = MyApplication.getSharedPref(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(IS_FIRSTRUN, false);
            editor.apply();

            Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
        getActivity().finish();
    }


}//Class
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.LoginRequest;
import com.hacathon.heavyequipmentrent.models.Responses.LoginResponse;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

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

    }

    public void onClickListeners(){
        imageView_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage();
            }
        });

        editTextUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Username clicked", Toast.LENGTH_LONG).show();
            }
        });

        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Password clicked", Toast.LENGTH_LONG).show();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidToSubmit()){
                    SubmitLogin();
                }
            }
        });
    }


    private void SubmitLogin(){
        LoginRequest req = new LoginRequest();
        req.setUsername(editTextUsername.getText().toString());
        req.setPassword(editTextPassword.getText().toString());

        ((MainActivity) getActivity()).showLoadingDialog(null, null);


        if (NetworkHelper.getInstance().isConnected()){

            Call<LoginResponse> cResponse = MyApplication.getRestClient().getApiService().loginService(req);
            cResponse.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                    if (isVisible() && !isDetached()){

                        ((MainActivity) getActivity()).hideLoadingDialog();
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final LoginResponse res = response.body();


                        createUserProfileAndSave();

//                    if (res.Response.ResponseCode == 0){
//
//                        SharedPreferences pref = AppController.getSharedPrefEncryp(getActivity().getApplicationContext());
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putLong(USER_ID_PARAM, res.Response.UserID);
//                        editor.putLong(USER_TYPE_PARAM, userType);
//                        editor.putString(USER_PASSWORD_PARAM , req.getPassword());
//                        editor.apply();
//
//
//                        if (userType == USER_TYPE_GULF){
//                            ((LoginAndVerifyActivity) getActivity()).GotoOTBForLoginFragment(res, VERIFY_OTP_SCREEN_ACCESS_TYPE_LOGIN , req, selectedGCCNationality);
//                        }else if (userType == USER_TYPE_VISITOR){
//                            ((LoginAndVerifyActivity) getActivity()).GotoOTBForLoginFragment(res, VERIFY_OTP_SCREEN_ACCESS_TYPE_LOGIN , req, selectedNationality);
//                        }else {
//                            ((LoginAndVerifyActivity) getActivity()).GotoOTBForLoginFragment(res, VERIFY_OTP_SCREEN_ACCESS_TYPE_LOGIN , req, null);
//                        }
//
//
//                    }
//                    else {
//
//                        AppController.getInstance().reportErrorToServer(
//                                "SERVER ERROR",
//                                LanguageManager.isCurrentLangARabic() ? response.body().Response.ResponseDescAr : response.body().Response.ResponseDescLa,
//                                cResponse.request().url().toString(),
//                                cResponse.request().body());
//
//
//
//
//                        if(LanguageManager.isCurrentLangARabic()){
//                            AppController.getInstance().reportError(res.Response.getResponseDescAr());
//                        }
//                        else{
//                            AppController.getInstance().reportError(res.Response.getResponseDescLa());
//                        }
//
//
//                    }
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

    private void createUserProfileAndSave(){

        MyApplication.getRealmInstance().beginTransaction();
        MyApplication.getRealmInstance().where(UserBean.class).findAll().deleteAllFromRealm();
        UserBean userBean = new UserBean();
        userBean.setUserID(11111111);
        MyApplication.getRealmInstance().copyToRealmOrUpdate(userBean);
        MyApplication.getRealmInstance().commitTransaction();

        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Home);
    }



    private boolean isValidToSubmit(){

        textInputLayout_username.setErrorEnabled(false);
        textInputLayout_password.setErrorEnabled(false);

        if (editTextUsername.getText().toString().isEmpty()){
            textInputLayout_username.setError(getString(R.string.login_valid_username_error_message));
            return false;
        }

        if (editTextUsername.getText().toString().length() < 5){
            textInputLayout_username.setError(getString(R.string.login_valid_username_length_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().isEmpty()){
            textInputLayout_password.setError(getString(R.string.login_valid_password_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().length() < 8){
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

            System.exit(0);
        }
        else {
            LanguageManager.setCurrentLocalLanguage(getActivity().getBaseContext(), "ar");
            SharedPreferences pref = MyApplication.getSharedPref(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(IS_FIRSTRUN, false);
            editor.apply();

            System.exit(0);
        }
    }


}//Class
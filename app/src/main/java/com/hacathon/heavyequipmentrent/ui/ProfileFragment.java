package com.hacathon.heavyequipmentrent.ui;

import static com.hacathon.heavyequipmentrent.Constants.Constants.IS_FIRSTRUN;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    View view;
    MainCallBacks mainCallBacks;
    ImageView imageView_language, imageView_logout;
    TextInputEditText user_name_profile;

    public ProfileFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static ProfileFragment newInstance(MainCallBacks mainCallBacks) {
        ProfileFragment fragment = new ProfileFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;

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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);


        initLayouts();
        onClickListeners();

        UserBean bean = Realm.getDefaultInstance().where(UserBean.class).findFirst();
        if (bean != null && bean.isValid()){
            user_name_profile.setText(bean.getDisplayName());
        }

        return view;
    }//OnCreateView

    private void initLayouts(){
        imageView_language = view.findViewById(R.id.imageView_language);
        imageView_logout = view.findViewById(R.id.imageView_logout);
        user_name_profile = view.findViewById(R.id.user_name_profile);
    }

    private void onClickListeners(){
        imageView_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage();
            }
        });

        imageView_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmDialog();
            }
        });
    }

    private void showLogoutConfirmDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(this.getString(R.string.settings_logout));
        alertDialog.setMessage(getString(R.string.settings_logout_confirm_msg));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LogoutAndClearData();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, this.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private void LogoutAndClearData(){
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().where(UserBean.class).findAll().deleteAllFromRealm();
        Realm.getDefaultInstance().commitTransaction();
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Login);
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
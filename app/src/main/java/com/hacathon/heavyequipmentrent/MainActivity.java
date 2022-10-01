package com.hacathon.heavyequipmentrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.ContinueOrderFragment;
import com.hacathon.heavyequipmentrent.ui.HomeFragment;
import com.hacathon.heavyequipmentrent.ui.LoginFragment;
import com.hacathon.heavyequipmentrent.ui.OrdersFragment;
import com.hacathon.heavyequipmentrent.ui.ProfileFragment;
import com.hacathon.heavyequipmentrent.ui.RegisterFragment;
import com.hacathon.heavyequipmentrent.ui.SelectEquipmentFragment;
import com.hacathon.heavyequipmentrent.ui.SettingsFragment;
import com.hacathon.heavyequipmentrent.ui.SubCategoryFragment;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainCallBacks {

    //FRAGMENT
    FragmentManager fragmentManager;
    ConstraintLayout cons_header;
    TextView txt_screenTitle;
    BottomNavigationView app_nav;
    ImageView imageView_arrow_back;
    ProgressDialog progress;


    public Long selectedCatId = -1L;
    public Long selectedSubCatId = -1L;
    public Long selectedEquipmentId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FM
        fragmentManager = getSupportFragmentManager();


        initLayouts();
        setClickListeners();

        UserBean bean = Realm.getDefaultInstance().where(UserBean.class).findFirst();
        if (bean != null && bean.isValid()){
            navigateTo(Constants.Navigations.Home);
        }else {
            navigateTo(Constants.Navigations.Login);
        }


        if (LanguageManager.isCurrentLangARabic()){
            imageView_arrow_back.setRotation(180);
        }

    }//OnCreate


    @Override
    protected void onResume() {
        super.onResume();

        //CHECK USER LOGGED IN DIRECT TO HOME OR DIRECT TO LOGIN
    }

    private void initLayouts(){
        cons_header = findViewById(R.id.cons_header);
        txt_screenTitle = findViewById(R.id.txt_screenTitle);
        app_nav = findViewById(R.id.app_nav);
        imageView_arrow_back = findViewById(R.id.imageView_arrow_back);
    }

    private void setClickListeners(){
        imageView_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentManager != null){
                    fragmentManager.popBackStack();
                }
            }
        });


        app_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        navigateTo(Constants.Navigations.Home);
                        break;
                    case R.id.requests:
                        navigateTo(Constants.Navigations.Orders);
                        break;
//                    case R.id.settings:
//                        navigateTo(Constants.Navigations.Settings);
//                        break;
                    case R.id.profile:
                        navigateTo(Constants.Navigations.Profile);
                        break;
                }
                return true;
            }
        });
    }

    public void showHideTopBackButton(Constants.ShowOrHide visibility){
        switch (visibility){
            case SHOW: imageView_arrow_back.setVisibility(View.VISIBLE); break;
            case HIDE: imageView_arrow_back.setVisibility(View.GONE); break;
        }
    }


    public void showHideTopActionBar(Constants.ShowOrHide visibility){
        switch (visibility){
            case SHOW: cons_header.setVisibility(View.VISIBLE); break;
            case HIDE: cons_header.setVisibility(View.GONE); break;
        }
    }

    public void showHideTopActionBarTitle(Constants.ShowOrHide visibility){
        switch (visibility){
            case SHOW: txt_screenTitle.setVisibility(View.VISIBLE); break;
            case HIDE: txt_screenTitle.setVisibility(View.GONE); break;
        }
    }

    public void setActionBarTitle(String title){
        txt_screenTitle.setText(title);
    }

    public void showHideBottomNavBar(Constants.ShowOrHide visibility){
        switch (visibility){
            case SHOW: app_nav.setVisibility(View.VISIBLE); break;
            case HIDE: app_nav.setVisibility(View.GONE); break;
        }
    }



    public void navigateTo(Constants.Navigations direction){
        switch (direction){
            case Register: fragmentManager.beginTransaction().replace(R.id.fragment_container, RegisterFragment.newInstance(this)).addToBackStack(null).commit(); break;
            case Login: {
                clearBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment.newInstance(this)).commit(); break;
            }
            case Home: {
                clearBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment.newInstance(this)).addToBackStack(null).commit();} break;
            case SUB_CATEGORY: fragmentManager.beginTransaction().replace(R.id.fragment_container, SubCategoryFragment.newInstance(this, selectedCatId)).addToBackStack(null).commit(); break;
            case SELECT_EQUIPMENT: fragmentManager.beginTransaction().replace(R.id.fragment_container, SelectEquipmentFragment.newInstance(this, selectedCatId, selectedSubCatId)).addToBackStack(null).commit(); break;
            case CONTINUE_ORDER: fragmentManager.beginTransaction().replace(R.id.fragment_container, ContinueOrderFragment.newInstance(this, selectedCatId, selectedSubCatId, selectedEquipmentId)).addToBackStack(null).commit(); break;
            case Orders: {
                clearBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, OrdersFragment.newInstance(this)).addToBackStack(null).commit(); break;}
            case Settings: {
                clearBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment.newInstance(this)).addToBackStack(null).commit(); break;}
            case Profile: {
                clearBackStack();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance(this)).addToBackStack(null).commit(); break;}
        }
    }

    public void clearBackStack(){
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() < 0){
            for (int i = 0 ; i < fragmentManager.getBackStackEntryCount()+1 ; i++){
                fragmentManager.popBackStack();
            }
        }
    }


    @Override
    public void onBackPressed() {
        //CUSTOM BACK BUTTON IN FRAGMENTS (VISIBLE FRAG)
//        if(Objects.requireNonNull(getVisibleFragment()).getClass() == paymentConfirmationFragment.class){
//        }
//        else{
//            super.onBackPressed();
//        }
    }

    private Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void showLoadingDialog(String title, String message){
        if (progress == null){
            progress = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            progress.setTitle(getString(R.string.loading));
            progress.setMessage(getString(R.string.loading));
            if (title != null && ! title.isEmpty()){
                progress.setTitle(getString(R.string.loading));
            }
            if (message != null && ! message.isEmpty()){
                progress.setTitle(getString(R.string.loading));
            }
            progress.setCancelable(false);
        }
        progress.show();
    }

    public void hideLoadingDialog(){
        if (progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageManager.checkCurrentLanguage(base));
    }


}//Class
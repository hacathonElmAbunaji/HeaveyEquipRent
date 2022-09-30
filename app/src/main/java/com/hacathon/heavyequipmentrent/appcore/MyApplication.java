package com.hacathon.heavyequipmentrent.appcore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Toast;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.network.RestClient;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.io.IOException;
import java.security.GeneralSecurityException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends android.app.Application {


    private static MyApplication mInstance;
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    public static LanguageManager mLanguage_Manager = new LanguageManager();
    private static RestClient restClient;
    private NetworkHelper networkHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        mInstance = this;
        initDB();
        setRestClient(new RestClient());
        networkHelper = new NetworkHelper(getApplicationContext());

    }

    public void initDB() {
        RealmConfiguration config;
        config = new RealmConfiguration.Builder().name("her.realm").deleteRealmIfMigrationNeeded().build();
        config.shouldDeleteRealmIfMigrationNeeded();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);
    }

    public static SharedPreferences getSharedPref(Context context){
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE);
    }

    public static Realm getRealmInstance(){
        return Realm.getDefaultInstance();
    }

    public static void setRestClient(RestClient restClient) {
        MyApplication.restClient = restClient;
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    public void reportError(String msg) {

        if(msg != null){
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }



//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(mLanguage_Manager.checkCurrentLanguage(base));
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mLanguage_Manager.checkCurrentLanguage(this);
//    }

}

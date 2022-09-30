package com.hacathon.heavyequipmentrent.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {
    Context context;
    Activity CurrentActivity;
    public static String APP_TOKEN = "";
    public static boolean IS_Connected = false;
    private static NetworkHelper mInstance;
    String android_id;
    public ProgressDialog progressBar;
    public static synchronized NetworkHelper getInstance() {
        return mInstance;
    }

    public NetworkHelper(Context context) {
        this.context = context;
        mInstance = this;

    }
    public  void setCurrentActivity(Activity Ac){
        this.CurrentActivity=Ac;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        IS_Connected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        return IS_Connected;
    }
}
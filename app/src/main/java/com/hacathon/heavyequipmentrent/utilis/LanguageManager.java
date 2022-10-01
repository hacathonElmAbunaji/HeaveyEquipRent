package com.hacathon.heavyequipmentrent.utilis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class LanguageManager {
    public static HashMap<String,String> languagesMap=new HashMap<>();

    public static String Current_Language_Name;
    public  static String Current_Language_Key;

    public LanguageManager() {
        super();
        initData();
    }

    public static void initData(){
        languagesMap= new HashMap<>();

        languagesMap.put("ar","العربية");
        languagesMap.put("en","English");


    }

    public static Context checkCurrentLanguage(Context context){

        ArrayList<String> langs_keys=new ArrayList<>( languagesMap.keySet());

        SharedPreferences sharedPref = MyApplication.getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        String lang= sharedPref.getString(context.getString(R.string.language_pref), null);


        if (lang==null){
            lang = "ar";
            Current_Language_Name = languagesMap.get(lang);
            Current_Language_Key = lang;
            editor.putString(context.getString(R.string.language_pref),lang);
            editor.apply();
        }
        else {
            if (langs_keys.contains(lang)) {
                Current_Language_Name = languagesMap.get(lang);
                Current_Language_Key = lang;
            } else {
                Current_Language_Name = "English";
                Current_Language_Key = "en";
            }
        }


        return updateResources(context , lang);

    }

    public static boolean isCurrentLangARabic(){
        if (Current_Language_Key != null && Current_Language_Key.equalsIgnoreCase("ar")){
            return true;
        }
        else {
            return false;
        }
    }

    public static void setCurrentLocalLanguage(Context context,String locLang){
        updateResources(context , locLang);

        SharedPreferences sharedPref = MyApplication.getSharedPref(context);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(context.getString(R.string.language_pref), locLang);
        editor.apply();

        Current_Language_Name = languagesMap.get(locLang);
        Current_Language_Key = locLang;
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }


    public static Locale getAppLanguageLocale(Context context){
        return context.getResources().getConfiguration().locale;
    }


    public HashMap<String, String> getLanguagesMap() {
        return languagesMap;
    }

    public void setLanguagesMap(HashMap<String, String> languagesMap) {
        this.languagesMap = languagesMap;
    }

    public String getCurrent_Language_Name() {
        return Current_Language_Name;
    }

    public void setCurrent_Language_Name(String current_Language_Name) {
        Current_Language_Name = current_Language_Name;
    }

    public String getCurrent_Language_Key() {
        return Current_Language_Key;
    }

    public void setCurrent_Language_Key(String current_Language_Key) {
        Current_Language_Key = current_Language_Key;
    }
}
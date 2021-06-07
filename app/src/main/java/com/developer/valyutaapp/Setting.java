package com.developer.valyutaapp;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 17.05.2018.
 */

public class Setting {
    public static final String LANG = "language";
    private static final String APP_SHARED_PREFS = "App_Shared";
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public Setting(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getLang(){
        return _sharedPrefs.getString(LANG, "en");
    }

    public String getBool (){
        return _sharedPrefs.getString("locked", "0");
    }
    public String getValue (){
        return _sharedPrefs.getString("locked", "0");
    }
    public void saveLang(String text){
        _prefsEditor.putString(LANG, text);
        _prefsEditor.commit();
    }

    public void saveBool(String bool){
        _prefsEditor.putString("locked", bool);
        _prefsEditor.commit();
    }
    public void saveValue (String value){
        _prefsEditor.putString("locked", value);
        _prefsEditor.commit();
    }
}

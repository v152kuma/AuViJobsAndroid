package com.auvi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.auvi.application.CSApplicationHelper;

public class CSShearedPrefence {

    private static SharedPreferences.Editor dataPrefsEditor;
    private static SharedPreferences dataPreferences;


    private static final String SESSION_ID = "SESSION_ID";
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String USER = "USER";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static void setAlreadyLaunch() {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        dataPrefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        dataPrefsEditor.apply();
    }

    public static boolean isAlreadyLaunch() {
        return getInstance().getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }

    public static SharedPreferences getInstance(){
        if(dataPreferences == null){
            dataPreferences =  CSApplicationHelper.application().getSharedPreferences("dataPrefs", Context.MODE_PRIVATE);
        }
        return dataPreferences;
    }

    public static void setIsLoggedIn(boolean data) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(IS_LOGIN, data);
        boolean temp = getInstance().getBoolean("IS_SHUT_MESSAGE", false);
        if(!data) {
            dataPrefsEditor.clear();
        }
        dataPrefsEditor.apply();
        setAlreadyLaunch();
    }

    public static boolean isLoggedIn() {
        return getInstance().getBoolean(IS_LOGIN, false);
    }

    public static void setUser(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(USER, user);
        dataPrefsEditor.apply();
    }

    public static String getUser() {
        return getInstance().getString(USER, "");
    }

    public static void setAccountRole(int id) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putInt("account_role", id);
        dataPrefsEditor.apply();
    }

    public static int getAccountRole() {
        return getInstance().getInt("account_role", 0);
    }


    public static void saveDeviceToken(String userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(DEVICE_TOKEN, userId);
        dataPrefsEditor.apply();
    }

    public static String getDeviceToken() {
        return getInstance().getString(DEVICE_TOKEN, "");
    }

}


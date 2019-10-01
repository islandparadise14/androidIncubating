package com.example.homework3.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String PREF_NAME = "yourssu-incubating";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static void setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}

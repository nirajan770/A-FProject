package com.abercrombiefitch.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nirajan on 9/27/2015.
 */
public class PrefUtils {
    private static final String TAG = "PrefUtils";

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    /**
     * Store the flag to check if the user learnt the navigation drawer
     */
    public static final String PREF_DRAWER_WELCOME = "pref_drawer_welcome";

    public static void init(Context context){
        Log.i(TAG, "PrefUtils Created");
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isDrawerWelcomeDone(final Context context){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        return mPref.getBoolean(PREF_DRAWER_WELCOME, false);
    }

    public static void markDrawerWelcomeDone(final Context context, boolean flag){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        mPref.edit().putBoolean(PREF_DRAWER_WELCOME, flag).commit();
    }

}

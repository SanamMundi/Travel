package com.example.travelwithme;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    public static SharedPreferences getSharedPref(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return pref;
    }

    public static void clearPreferences(Context context){
        SharedPreferences preferences =getSharedPref(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public  static void setLongPrefVal(Context mContext, String key, Long value){
        SharedPreferences.Editor edit= getSharedPref(mContext).edit();
        if(key.equals("")|| key==null){
            return;
        }else {
            edit.putLong(key, value);
            edit.commit();
        }
    }

    public static void setStringPrefVal(Context mContext, String key, String value) {
        SharedPreferences.Editor edit = getSharedPref(mContext).edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void setIntPrefVal(Context mContext, String key, int value) {
        SharedPreferences.Editor edit = getSharedPref(mContext).edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void setBooleanPrefVal(Context mContext, String key, boolean value){
        SharedPreferences.Editor edit = getSharedPref(mContext).edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static Long getLongPrefVal(Context mContext,String key){
        SharedPreferences pref= getSharedPref(mContext);
        Long val= null;
        if(pref.contains(key)){
            val= pref.getLong(key, 0);
        }else {
            val= Long.valueOf(0);
        }
        return val;
    }

    public static boolean getBooleanPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        boolean val = false;
        if(pref.contains(key))
            val = pref.getBoolean(key,false);
        else
            val = false;
        return val;
    }

    public static String getPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        String val = "";
        if(pref.contains(key))
            val = pref.getString(key, "");
        else
            val = "";
        return val;
    }

    public static int getIntPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        int val = 0;
        if(pref.contains(key)) val = pref.getInt(key, 0);
        return val;
    }
}

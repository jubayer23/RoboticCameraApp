package com.creative.roboticcameraapp.sharedprefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.creative.roboticcameraapp.appdata.AppConstant;


public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "com.creative.roboticcameraapp";

    private static final String KEY_LOGIN_TYPE = "login_type";

    private static final String KEY_SORT_ORDER_FOR_SINGLE_ROW = "sort_order_for_single_row";

    private static final String KEY_SORT_ORDER_FOR_MULTI_ROW = "sort_order_for_multi_row";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void setLoginType(String type) {
        editor = pref.edit();

        editor.putString(KEY_LOGIN_TYPE, type);

        // commit changes
        editor.commit();
    }

    public String getLoginType() {
        return pref.getString(KEY_LOGIN_TYPE, "");
    }


    public void setSortOrderForSingleRow(int type) {
        editor = pref.edit();

        editor.putInt(KEY_SORT_ORDER_FOR_SINGLE_ROW, type);

        // commit changes
        editor.commit();
    }

    public int getSortOrderForSingleRow() {
        return pref.getInt(KEY_SORT_ORDER_FOR_SINGLE_ROW, AppConstant.ASENDING);
    }

    public void setSortOrderForMultiRow(int type) {
        editor = pref.edit();

        editor.putInt(KEY_SORT_ORDER_FOR_MULTI_ROW, type);

        // commit changes
        editor.commit();
    }

    public int getSortOrderMultiRow() {
        return pref.getInt(KEY_SORT_ORDER_FOR_MULTI_ROW, AppConstant.ASENDING);
    }

}
package com.creative.roboticcameraapp.appdata;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.creative.roboticcameraapp.database.SqliteDb;
import com.creative.roboticcameraapp.sharedprefs.PrefManager;


public class AppController extends Application {


    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    private static SqliteDb sqliteDbInstance;

    private PrefManager pref;


    private float scale;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        pref = new PrefManager(this);

        sqliteDbInstance = new SqliteDb(this);
        sqliteDbInstance.open();

        this.scale = getResources().getDisplayMetrics().density;


        for(int i =0;i< AppConstant.bracketing_style.length;i++)
        {
            AppConstant.bracketing_style_map.put(AppConstant.bracketing_style[i],i+1);
        }

        for(int i =0;i< AppConstant.direction.length;i++)
        {
            AppConstant.direction_map.put(AppConstant.direction[i],i+1);
        }

        // FacebookSdk.sdkInitialize(getApplicationContext());

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized SqliteDb getsqliteDbInstance() {
        return sqliteDbInstance;
    }

    public PrefManager getPrefManger() {
        if (pref == null) {
            pref = new PrefManager(this);
        }

        return pref;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public int getPixelValue(int dps) {
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }



}
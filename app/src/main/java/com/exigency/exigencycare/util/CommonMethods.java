package com.exigency.exigencycare.util;

/* CommonMethods contain all Common methods for app
 * 
 * @author GIRNARSOFT
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;


@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class CommonMethods {

   static Activity activity;
    Intent intent = new Intent();
    Object listener;
    ProgressDialog pd;

    public static String Signature = "E662A741-0B91-4E04-BFC0-F0D8EFA608B0";

    // live url
    public static final String API = "http://www.exigencycare.com/api";
    public static final String API_BOOK = "http://droozo.girnarlive.com/booking_services";

    // local url
    //public static final String API = "http://drozooapi.sez.com/user_services";
    //public static final String API_BOOK = "http://drozooapi.sez.com/booking_services";

    @SuppressLint("NewApi")
    public CommonMethods(Activity activity1, Object listener) {
        this.activity = activity1;
        this.listener = listener;
    }

    public void showProgressDialog() {
        pd = new ProgressDialog(activity);
        pd.setMessage("loading");
        pd.show();
    }

    public static void clearApplicationData()
    {
        File cache = activity.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
        return dir.delete();
    }


    public void dismissProgressDialog() {
        pd.dismiss();
    }


    public void hideKeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * This method is used for get the connectivity status
     *
     * @return
     */
    public boolean getConnectivityStatus() {
        ConnectivityManager connManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null)
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        else
            return false;
    }

}
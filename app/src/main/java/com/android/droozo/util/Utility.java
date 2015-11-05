package com.android.droozo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.droozo.droozoapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Contain all unique members
 * Created by GSS-Vishnu Kant on 1/10/15.
 */
public class Utility {

    public static Utility sUtility = null;
    public static Intent sIntent = new Intent();

    private Utility() {

    }

    public static Utility getUtilityInstance() {

        if (sUtility == null) {
            sUtility = new Utility();
        }
        return sUtility;
    }

    //show message
    public static void showMessage(String msg, Context con) {
        Toast.makeText(con, "" + msg, Toast.LENGTH_SHORT).show();
    }


    //check email Validation
    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    //return String value from edittext
    public static String getStringFromEditText(EditText ed) {
        return ed.getText().toString().trim();
    }


    /*********************
     * set shared preferences
     **************************/
    public static void SetPreferences(Context con, String key, String value) {
        // save the data
        SharedPreferences preferences = con.getSharedPreferences("prefs_login",
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /******************
     * get shared preferences
     *******************/
    public static String getPreferences(Context con, String key) {
        SharedPreferences sharedPreferences = con.getSharedPreferences(
                "prefs_login", 0);
        String value = sharedPreferences.getString(key, "0");
        return value;

    }

    /**********************
     * set shared preferences in int
     *********************************/
    public static void SetPreferencesInteger(Context con, String key, int value) {
        // save the data
        SharedPreferences preferences = con.getSharedPreferences("prefs_login",
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /*********************
     * get shared preferences in int
     ***********************/
    public static int getPreferencesInteger(Context con, String key) {
        SharedPreferences sharedPreferences = con.getSharedPreferences(
                "prefs_login", 0);
        int value = sharedPreferences.getInt(key, 0);
        return value;

    }

    public static String getStringExtraProcess(String key, Activity context) {
        try {
            return context.getIntent().getStringExtra(key);
        } catch (Exception e) {
            return "";
        }
    }


    public static String getStringExtraBundle(String key, Activity context) {
        Bundle getBundle = null;
        try {
            getBundle = context.getIntent().getExtras();
            return getBundle.getString(key);
        } catch (Exception e) {
            return "";
        }
    }


    /*******
     * do animated activity
     ********/
    public static void doAnim(Activity act, String flag) {
        if (flag.equals("left")) {
            act.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        } else if (flag.equals("right")) {
            act.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        } else if (flag.equals("no")) {
            act.overridePendingTransition(0, 0);
        }
    }


    /**
     * start activity without finish, without any value
     */
    public static void doStartActivityWithoutFinish(Activity act,
                                                    Class cls, String anim_left_right_no) {
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity with finish, without any value
     */
    public static void doStartActivityWithFinish(Activity act,
                                                 Class cls, String anim_left_right_no) {
        act.startActivity(sIntent.setClass(act, cls));
        act.finish();
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity without finish, with String value
     */
    public static void doStartActivityWithoutFinishStringValue(Activity act,
                                                               Class cls, String key, String value, String anim_left_right_no) {
        sIntent.putExtra(key, value);
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity without finish, with 2 String value
     */
    public static void doStartActivityWithoutFinishString2Value(Activity act,
                                                                Class cls, String key1, String value1, String key2, String value2, String anim_left_right_no) {
        sIntent.putExtra(key1, value1);
        sIntent.putExtra(key2, value2);
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity without finish, with 3 String value
     */
    public static void doStartActivityWithoutFinishString3Value(Activity act,
                                                                Class cls, String key1, String value1, String key2, String value2, String key3, String value3, String anim_left_right_no) {
        sIntent.putExtra(key1, value1);
        sIntent.putExtra(key2, value2);
        sIntent.putExtra(key3, value3);
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity without finish, with int value
     */
    public static void doStartActivityWithoutFinishIntValue(Activity act,
                                                            Class cls, String key, int value, String anim_left_right_no) {
        sIntent.putExtra(key, value);
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }

    /**
     * start activity without finish, with Bundle
     */
    public static void doStartActivityWithoutFinishBundle(Activity act,
                                                          Class cls, Bundle bundle, String anim_left_right_no) {
        sIntent.putExtras(bundle);
        act.startActivity(sIntent.setClass(act, cls));
        doAnim(act, anim_left_right_no);
    }


    public static JSONObject getLocationFromAddress(String strAddressGeoCodingUrl) {

        try {
            URL url;
            HttpURLConnection urlConnection = null;
            JSONObject response = null;

            try {
                url = new URL(strAddressGeoCodingUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == 200) {
                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("TAG", responseString);
                    response = new JSONObject(responseString);
                } else {
                    Log.v("TAG", "Response code:" + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}

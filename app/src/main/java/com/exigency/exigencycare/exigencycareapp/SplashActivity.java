package com.exigency.exigencycare.exigencycareapp;

/**
 * Splash Screen
 *
 * @author Vishnu Kant
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends Activity {

    private Activity _activitySplash;
    private ImageView splashCenterImgLogo;
    private Animation animFadein;
    private CommonMethods _commonMethods;
    static ArrayList<String> arrCity = new ArrayList<String>();
    static ArrayList<String> arrCityId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        setBodyUI();

    }// end onCreate()------------------

    private void setBodyUI() {
        _activitySplash = this;
        _commonMethods = new CommonMethods(_activitySplash, this);
        splashCenterImgLogo = (ImageView) findViewById(R.id.splashCenterImgLogo);


        if (Utility.getUtilityInstance().getConnectivityStatus(_activitySplash)) {
            getCityData();
        } else {
            Toast.makeText(_activitySplash, "Please check your network connection.", Toast.LENGTH_SHORT).show();
        }


    }// end setBodyUI()----------------


    /**
     * send forgot password request on server
     */
    private void getCityData() {

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("type", "city");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.getCityList(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {

            }

            @Override
            public void success(ExigencyModel result, Response arg2) {


                for (int i = 0; i < result.getCities().size(); i++) {
                    arrCity.add(result.getCities().get(i).getCityName());
                    arrCityId.add(result.getCities().get(i).getCityID());
                }

                SharedPreferences prefs = getSharedPreferences("Exigency", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=prefs.edit();

                Set<String> set = new HashSet<String>();
                set.addAll(arrCity);
                edit.putStringSet("City", set);
                edit.commit();

                Set<String> set2 = new HashSet<String>();
                set2.addAll(arrCityId);
                edit.putStringSet("CityId", set2);
                edit.commit();

//Set<String> set = prefs.getStringSet("yourKey", null);
                //List<String> sample=new ArrayList<String>(set);

                if (Utility.getPreferencesInteger(_activitySplash, "USERID")==0) {
                    Utility.getUtilityInstance().doStartActivityWithFinish(_activitySplash, LoginRegistrationActivity.class, "right");
                } else {
                    Utility.getUtilityInstance().doStartActivityWithFinish(_activitySplash, WelcomeActivity.class, "right");
                }

                // check status code if 500 then show message otherwise continue

            }
        });
    }// end forgotPasswordService()---------------


}// end main class------------------
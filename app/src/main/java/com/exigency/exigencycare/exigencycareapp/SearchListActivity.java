package com.exigency.exigencycare.exigencycareapp;

/**
 * Search List screen where user search doctor
 *
 * @author GSS- Vishnu Kant
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.adapter.SearchItemAdapter;
import com.exigency.exigencycare.adapter.SearchSpecialistAdapter;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar mToolbar;
    private Activity _activity = this;
    private TextView txtHeader;
    private ImageView imgfilter;
    private ListView lvSearchList;
    private LinearLayout llbottom, llTotalPrice;
    //by default take phycian
    private String WhichCategoryDoctorShow = "physician";
    ResponseData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_activity);
        toolBarMaintain();
        setBodyUI();
    }

    private void toolBarMaintain() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("DOCTORS");


        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }// end toolBarMaintain()---------------


    public void setBodyUI() {

        lvSearchList = (ListView) findViewById(R.id.lvSearchItem);
       /* lvSearchList.setAdapter(new SearchItemAdapter(_activity));*/
        lvSearchList.setOnItemClickListener(this);
        if (Utility.getUtilityInstance().getConnectivityStatus(_activity)) {
            Utility.runProgressDialog(_activity);
            getHomeData();
        } else {
            Toast.makeText(_activity, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }

    }// end setBodyUI---------------------

    /**
     * send forgot password request on server
     */
    private void getHomeData() {

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("city_id", "" + Utility.getPreferences(_activity, "CITYID"));
        request.put("department", "" + getIntent().getStringExtra("DepID"));
        request.put("name", "");
        request.put("city", "");
        request.put("pincode", "");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.searchdoctor(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                ExigencyModel data = result;

                if (result.getDoctors() == null || result.getDoctors().size() == 0) {
                    Toast.makeText(_activity, "Oop's No record found!", Toast.LENGTH_SHORT).show();
                } else {
                    lvSearchList.setAdapter(new SearchSpecialistAdapter(_activity, result.getDoctors()));
                }

                Utility.stopProgressDialog();
            }
        });
    }// end forgotPasswordService()---------------

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(_activity, DetailsActivity.class);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    /**
     * filter search results
     * Vishnu Kant
     */
    private void filterSearchItem() {
        Utility.doStartActivityWithoutFinish(_activity, FilterSearchHealthActivity.class, "right");

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    public void showMessage(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();

    }

    /**
     * Show message for short time
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }

}// end main class--------------------
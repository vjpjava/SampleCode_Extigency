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
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.adapter.SearchItemAdapter;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpecialistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
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
        WhichCategoryDoctorShow = Utility.getStringExtraProcess("TYPE", this);

        // call service
        doctorListProcess(WhichCategoryDoctorShow, Utility.getStringExtraProcess("HEADER", this));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText(WhichCategoryDoctorShow.toUpperCase());

        imgfilter = (ImageView) mToolbar
                .findViewById(R.id.imgRight);
        imgfilter.setVisibility(View.VISIBLE);
        imgfilter.setBackgroundResource(R.drawable.filter);
        imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSearchItem();
            }
        });

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
        lvSearchList.setOnItemClickListener(this);

        llbottom = (LinearLayout) findViewById(R.id.llbottom);
        llTotalPrice = (LinearLayout) findViewById(R.id.llTotalPrice);
        llbottom.setVisibility(View.GONE);
        llTotalPrice.setVisibility(View.GONE);

    }// end setBodyUI---------------------

    /**
     * get 4 category data like doctors
     */
    public void doctorListProcess(String type, String headerType) {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("speciality-name", type);
        request.put("type", "");

        if (Utility.getStringExtraProcess("WHICH", this).equalsIgnoreCase("SearchHealthProvider")) {
            request.put("city", "");
            request.put("location", "");
        } else {
            request.put("city", Utility.getStringExtraProcess("CITY", this));
            request.put("location", Utility.getStringExtraProcess("LOCATION", this));
        }

        request.put("starting_limit", "0");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getDoctorList(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {

                data = result.getResponseData();
                if (result.getStatusCode() == 200) {
                    lvSearchList.setAdapter(new SearchItemAdapter(_activity));
                } else {
                    showToast("" + data.getMessage());
                }
            }
        });
    }// end HealthProviderProcess()-----------------


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(_activity, DetailsActivity.class);
        intent.putExtra("OBJECT", data.getDoclist().get(position));
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }

    /**
     * filter search results
     * Vishnu Kant
     */
    private void filterSearchItem() {
        Utility.doStartActivityWithoutFinishString3Value(_activity, FilterSearchHealthActivity.class, "SPECIAL", WhichCategoryDoctorShow, "CITY", "", "DEFAULT", "XYZ", "right");
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
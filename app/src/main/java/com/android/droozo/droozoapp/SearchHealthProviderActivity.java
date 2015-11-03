package com.android.droozo.droozoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.Api.ApiCaller;
import com.android.droozo.Model.HealthProviderModel;
import com.android.droozo.Model.ResponseData;
import com.android.droozo.adapter.CustomGridSearch;
import com.android.droozo.util.CommonMethods;
import com.android.droozo.util.SelectedLinearLayout;
import com.android.droozo.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * search doctor according to name and location
 * Created by GSS-Vishnu Kant on 29/9/15.
 */
public class SearchHealthProviderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Toolbar mToolbar;
    private Activity _activity = this;
    private GridView gridView;
    private ImageView mImgSearch;
    private SelectedLinearLayout mSelectedLinearLayout;
    private LinearLayout mllDoctor, mllDiagonstic, mllAmbulance, mllPharmacy;
    ResponseData data;
    private String headerType = "Doctor";

    //if response is wrong then adapter wont set
    private boolean mFlagForResponse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_health_provider);

        toolBarMaintain();
        setBodyUI();
    }

    /**
     * maintain toolbar ui and event action
     */
    private void toolBarMaintain() {
        HealthProviderProcess();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.back);

        mImgSearch = (ImageView) mToolbar
                .findViewById(R.id.imgRight);
        mImgSearch.setVisibility(View.VISIBLE);
        mImgSearch.setBackgroundResource(R.drawable.search_white);
        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, SearchActivity.class, "right");
            }
        });


        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText(getResources().getString(R.string.search_health_providers));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }// end toolBarMaintain()-------------------------

    /**
     * used for intialize view
     */
    public void setBodyUI() {
        mSelectedLinearLayout = (SelectedLinearLayout) findViewById(R.id.llHeader);
        mSelectedLinearLayout.setselected(0);

        mllDoctor = (LinearLayout) findViewById(R.id.llDoctor);
        mllDiagonstic = (LinearLayout) findViewById(R.id.llDiagnostic);
        mllAmbulance = (LinearLayout) findViewById(R.id.llAmbulance);
        mllPharmacy = (LinearLayout) findViewById(R.id.llPharmacy);

        mllDoctor.setOnClickListener(this);
        mllDiagonstic.setOnClickListener(this);
        mllAmbulance.setOnClickListener(this);
        mllPharmacy.setOnClickListener(this);

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setOnItemClickListener(this);


    }// end setBodyUI()----------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDoctor:
                if (mFlagForResponse == false) {
                    showToast("Please check network connection!");
                } else {
                    // layout show clicked
                    mSelectedLinearLayout.setselected(0);
                    headerType = "Doctor";
                    if (data.getDoctor() != null) {
                    } else {
                        showToast("There is no records!");
                    }
                    gridView.setAdapter(new CustomGridSearch(_activity, data.getDoctor(), data.getUri()));
                }
                break;
            case R.id.llDiagnostic:

                if (mFlagForResponse == false) {
                    showToast("Please check network connection!");
                } else {
                    // layout show clicked
                    mSelectedLinearLayout.setselected(1);
                    headerType = "Diagnostic";
                    if (data.getDiagnostic() != null) {
                    } else {
                        showToast("There is no records!");
                    }
                    gridView.setAdapter(new CustomGridSearch(_activity, data.getDiagnostic(), data.getUri()));
                }


                break;
            case R.id.llAmbulance:

                if (mFlagForResponse == false) {
                    showToast("Please check network connection!");
                } else {
                    // layout show clicked
                    mSelectedLinearLayout.setselected(2);
                    headerType = "Ambulance";
                    if (data.getAmbulance() != null) {
                    } else {
                        showToast("There is no records!");
                    }
                    gridView.setAdapter(new CustomGridSearch(_activity, data.getAmbulance(), data.getUri()));
                }
                break;
            case R.id.llPharmacy:

                if (mFlagForResponse == false) {
                    showToast("Please check network connection!");
                } else {
                    // layout show clicked
                    mSelectedLinearLayout.setselected(3);
                    headerType = "Pharmacy";
                    if (data.getPharmacy() != null) {
                    } else {
                        showToast("There is no records!");
                    }
                    gridView.setAdapter(new CustomGridSearch(_activity, data.getPharmacy(), data.getUri()));
                }
                break;


            default:
                break;
        }
    }//end onClick()---------------------


    /**
     * get 4 category data like doctors
     */
    public void HealthProviderProcess() {
        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("service_uuidgen", CommonMethods.Signature);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getHealthProvider(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
                mFlagForResponse = false;
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {

                data = result.getResponseData();
                if (result.getStatusCode() == 200) {
                    gridView.setAdapter(new CustomGridSearch(_activity, data.getDoctor(), data.getUri()));
                } else {
                    showToast("" + data.getMessage());
                }
            }
        });
    }// end HealthProviderProcess()-----------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    /**
     * Show message for short time
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Utility.doStartActivityWithoutFinishString3Value(_activity, SearchListActivity.class, "TYPE", data.getDoctor()[position], "WHICH", "SearchHealthProvider", "HEADER", headerType, "right");
    }
}// end main class--------------

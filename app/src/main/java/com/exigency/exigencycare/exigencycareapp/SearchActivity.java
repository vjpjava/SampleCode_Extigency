package com.exigency.exigencycare.exigencycareapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.adapter.SearchCityAdapter;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * search doctor according to name and location
 * Created by  on 29/9/15.
 */
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar mToolbar;
    private Activity _activity = this;
    private ListView lvView;
    private EditText edSearchName, edSearchLocation;
    private TextView txtSearchCity;
    ResponseData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setBodyUI();
    }// end onCreate()-----------------

    public void setBodyUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.close);

        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("SEARCH");
        TextView txtSearch = (TextView) mToolbar
                .findViewById(R.id.txtRight);
        txtSearch.setText("SEARCH");

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_activity, SearchListActivity.class);
                intent.putExtra("TYPE", edSearchName.getText().toString());
                intent.putExtra("WHICH", "SEARCH");
                intent.putExtra("HEADER", "");
                intent.putExtra("CITY", txtSearchCity.getText().toString());
                intent.putExtra("LOCATION", edSearchLocation.getText().toString());
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(intent);
            }
        });


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvView = (ListView) findViewById(R.id.grid);
        lvView.setOnItemClickListener(this);

        edSearchName = (EditText) findViewById(R.id.edSearchName);
        edSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    callServiceForDoctor(s.toString());
                }

            }
        });

        txtSearchCity = (TextView) findViewById(R.id.txtSearchCity);
        txtSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edSearchName.clearFocus();
                edSearchLocation.clearFocus();
                callCityService();
            }
        });

        edSearchLocation = (EditText) findViewById(R.id.edSearchLocation);
        edSearchLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtSearchCity.getText().toString().trim().length() > 0) {
                        callServiceForLocation(txtSearchCity.getText().toString());
                    } else {
                        showToast("Please select the city!");
                    }
                } else {
                }
            }
        });


    }// end setBodyUI()----------------

    /**
     * get 4 category data like doctors
     */
    public void callServiceForDoctor(String strName) {
        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("query", strName);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getDoctorSpecialtiy(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {

                data = result.getResponseData();
                if (result.getStatusCode() == 200) {
                    lvView.setAdapter(new SearchCityAdapter(_activity, data.getDoc_name_cum_speciality()));
                } else {
                    showToast("" + data.getMessage());
                }
            }
        });
    }// end HealthProviderProcess()-----------------


    /**
     * get 4 category data like doctors
     */
    public void callCityService() {
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
        git.getCity(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {

                data = result.getResponseData();
                if (result.getStatusCode() == 200) {
                    lvView.setAdapter(new SearchCityAdapter(_activity, data.getCity_list()));
                    try {
                        txtSearchCity.setText(data.getCity_list()[0]);
                    } catch (Exception e) {

                    }
                } else {
                    showToast("" + data.getMessage());
                }
            }
        });
    }// end HealthProviderProcess()-----------------


    /**
     * get 4 category data like doctors
     */
    public void callServiceForLocation(String strCity) {
        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("city", strCity.trim());
        request.put("location", edSearchLocation.getText().toString().trim());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getLocationByCity(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                data = result.getResponseData();
                if (result.getStatusCode() == 200) {
                    lvView.setAdapter(new SearchCityAdapter(_activity, data.getLocation_list()));
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

        //check which edittext
        if (edSearchName.isFocused()) {
            edSearchName.setText(data.getDoc_name_cum_speciality()[position]);
        } else if (edSearchLocation.isFocused()) {
            edSearchLocation.setText(data.getLocation_list()[position]);
        } else {
            txtSearchCity.setText(data.getCity_list()[position]);
        }

    }
}// end main class------------------

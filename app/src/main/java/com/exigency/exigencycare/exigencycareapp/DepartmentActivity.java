package com.exigency.exigencycare.exigencycareapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.adapter.CustomGridSearch;
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
public class DepartmentActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Activity _activity = this;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deplist_activity);

        toolBarMaintain();
        setBodyUI();
    }

    /**
     * maintain toolbar ui and event action
     */
    private void toolBarMaintain() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.green_bg));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.back);


        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("DEPARTMENTS");

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

        gridView = (GridView) findViewById(R.id.grid);
Utility.runProgressDialog(_activity);
        departmentProviderProcess();
    }// end setBodyUI()----------------------------


    /**
     * get 4 category data like doctors
     */
    public void departmentProviderProcess() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("type", "list");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.Deptlist(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
                System.out.println("Response " + arg0.toString());
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                Utility.stopProgressDialog();
                gridView.setAdapter(new CustomGridSearch(_activity, result.getDepartments()));
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


}// end main class--------------

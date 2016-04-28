package com.exigency.exigencycare.exigencycareapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;
import com.rey.material.widget.EditText;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Edit user profile
 * Created by  on 29/9/15.
 */
public class EditProfileActivity extends AppCompatActivity {
    private View view;
    private Toolbar mToolBar;
    private Activity mActivty = this;
    private EditText edFirstName, edLastName, edEmailId, edContactNo;
    private EditText edNewPassword, edConfirmPassword;
    private int userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_fragment);
        userid = Utility.getPreferencesInteger(mActivty, "USERID");
        toolBarManage();
        setBodyUI();

    }

    private void toolBarManage() {
        mToolBar = (Toolbar) findViewById(R.id.llToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setNavigationIcon(R.drawable.back);

        TextView txtHeader = (TextView) mToolBar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("EDIT PROFILE");
        TextView txtSave = (TextView) mToolBar
                .findViewById(R.id.txtRight);
        txtSave.setText("SAVE");

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }// end toolBarManage()---------------------


    private void setBodyUI() {
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edLastName = (EditText) findViewById(R.id.edLastName);
        edEmailId = (EditText) findViewById(R.id.edEmailId);
        edContactNo = (EditText) findViewById(R.id.edContactNO);
        edConfirmPassword = (EditText) findViewById(R.id.edConfirmPassword);
        edNewPassword = (EditText) findViewById(R.id.edNewPassword);


        setData();

    }// end setBodyUI()---------------------


    private void setData() {
        edFirstName.setText(Utility.getStringExtraBundle("FNAME", mActivty));
        edLastName.setText(Utility.getStringExtraBundle("LNAME", mActivty));
        edEmailId.setText(Utility.getStringExtraBundle("EMAIL", mActivty));
        edContactNo.setText(Utility.getStringExtraBundle("MOBILE", mActivty));
    }


    /**
     * check validation
     */

    private void checkValidation() {
        if (edFirstName.getText().toString().equalsIgnoreCase("")) {
            edFirstName.setError("Please enter First Name!");
        } else if (edLastName.getText().toString().equalsIgnoreCase("")) {
            edLastName.setError("Please enter Last Name!");
        } else if (edEmailId.getText().toString().equalsIgnoreCase("")) {
            edEmailId.setError("Please enter Email Id!");
        } else if (!Utility.isValidEmail(edEmailId.getText().toString())) {
            edEmailId.setError("Please enter valid sEmail Id!");
        } else if (edContactNo.getText().toString().equalsIgnoreCase("")) {
            edContactNo.setError("Please enter Contact No!");
        } else if (edNewPassword.getText().toString().trim().length() <= 0) {
            edNewPassword.setError("Password field shouldn't be blank");
        } else if (edNewPassword.getText().toString().trim().length() <= 5 || edNewPassword.getText().toString().trim().length() >= 16) {
            edNewPassword.setError("Password should be between 6 to 15 characters");
        } else if (edConfirmPassword.getText().toString().trim().length() <= 0) {
            edConfirmPassword.setError("Password field shouldn't be blank");
        } else if (edConfirmPassword.getText().toString().trim().length() <= 5 || edConfirmPassword.getText().toString().trim().length() >= 16) {
            edConfirmPassword.setError("Password should be between 6 to 15 characters");
        } else if (!edConfirmPassword.getText().toString().trim().equals(edNewPassword.getText().toString().trim())) {
            Utility.showMessage("New Password and Confirm New Password should be same", mActivty);
        } else {
            updateProfileInfo();
        }

    }// end checkValidation()-----------------


    /**
     * send profile new info on server
     */
    private void updateProfileInfo() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("email", "");
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("id", String.valueOf(userid));
        request.put("firstname", edFirstName.getText().toString().trim());
        request.put("lastname", edLastName.getText().toString().trim());
        request.put("phone", edContactNo.getText().toString().trim());
        request.put("ppassword", edNewPassword.getText().toString().trim());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.updateProfile(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.showMessage("failure " + arg0.toString(), mActivty);
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();
                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                    Utility.showMessage("" + data.getMessage(), mActivty);
                } else {
                    Utility.showMessage("" + data.getMessage(), mActivty);
                    onBackPressed();
                }
            }
        });
    }// end forgotPasswordService()---------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(mActivty, "left");
    }

}
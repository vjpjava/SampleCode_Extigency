package com.exigency.exigencycare.exigencycareapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.util.CircleImageView;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.ImageOperationUtil;
import com.exigency.exigencycare.util.SelectedLinearLayout;
import com.exigency.exigencycare.util.Utility;
import com.facebook.CallbackManager;
import com.rey.material.widget.EditText;

import java.io.File;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements
        OnClickListener {
    private Activity _activity;
    private EditText _edName, _edEmail, _edPassword,
            _edConfirmPassword, _edPhone;
    private ToggleButton _tgButton;
    private View _dividerCategory;
    private CircleImageView takeImg;

    private final int RESULT_CAMERA = 100;
    private final int RESULT_GALLERY = 200;

    private Bitmap mUserBitmap;
    private ImageOperationUtil imageUtil = null;
    private String mFilePath = null;
    private Toolbar mToolbar;
    private CallbackManager callbackManager;
    private LinearLayout mllFb;
    private CommonMethods mCommonMethods;
    private int userid;
    private String mFBID, mFBName, mFBEmail;
    private File mFileImage, outputFile;
    private Bitmap mBitmapFB;
    private SelectedLinearLayout linearLayout1;

    private LinearLayout llFirst, llSecond;

    private int userType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.signup_activity);

        toolbarMaintain();

        setBodyUI();
    }// end onCreate()--------------


    /**
     * proceed toolbar according to page
     */
    private void toolbarMaintain() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.back);

        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText(getResources().getString(R.string.sign_up));

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mToolbar.findViewById(R.id.txtRight).setOnClickListener(this);
    }// end toolbarMaintain()--------------------


    private void setBodyUI() {
        _activity = this;
        mCommonMethods = new CommonMethods(_activity, this);

        linearLayout1 = (SelectedLinearLayout) findViewById(R.id.linearLayout1);
        linearLayout1.setselected(0);

        llFirst = (LinearLayout) findViewById(R.id.llFirst);
        llSecond = (LinearLayout) findViewById(R.id.llSecond);
        llFirst.setOnClickListener(this);
        llSecond.setOnClickListener(this);
        initializeView();
        //typeOFsignUpUser();
        findViewById(R.id.llRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        imageUtil = new ImageOperationUtil(_activity);

    }// end setBodyUI()-----------------

    /**
     * used for find all view
     */
    private void initializeView() {

        _edName = (EditText) findViewById(R.id.edName);
        _edEmail = (EditText) findViewById(R.id.edEmail);
        _edPassword = (EditText) findViewById(R.id.edPassword);
        _edConfirmPassword = (EditText) findViewById(R.id.edConPassword);
        _edPhone = (EditText) findViewById(R.id.edNumber);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRight:
                checkValidation();
                break;

            case R.id.llFirst:
                userType = 0;
                linearLayout1.setselected(0);
                break;
            case R.id.llSecond:
                userType = 1;
                linearLayout1.setselected(1);
                break;

            /*case R.id.takeImg:
                popupSelectionForImage();
                break;*/

            default:
                break;
        }

    }// end onClick()-----------------


    private void checkValidation() {

        if (Utility.getStringFromEditText(_edName).length() <= 0) {
            _edName.setError("Please enter a Name!");
        } else if (Utility.getStringFromEditText(_edEmail).length() <= 0) {
            _edEmail.setError("Please enter a valid email address!");
        } else if (!Utility.isValidEmail(Utility.getStringFromEditText(_edEmail))) {
            _edEmail.setError("Please enter a valid email address!");
        } else if (Utility.getStringFromEditText(_edPhone).length() <= 0) {
            _edPhone.setError("Please enter a valid phone number!");
        } else if (Utility.getStringFromEditText(_edPassword).length() <= 0) {
            _edPassword.setError("Please enter your password!");
        } else if (Utility.getStringFromEditText(_edPassword).length() <= 5) {
            _edPassword.setError("Password length must be between 6 to 15!");
        } else if (Utility.getStringFromEditText(_edConfirmPassword).length() <= 0) {
            _edConfirmPassword.setError("Please enter your confirm password!");
        } else if (Utility.getStringFromEditText(_edConfirmPassword).length() <= 5) {
            _edConfirmPassword.setError("Password length must be between 6 to 15!");
        } else if (!Utility.getStringFromEditText(_edConfirmPassword).equals(Utility.getStringFromEditText(_edPassword))) {
            _edConfirmPassword.setError("Password and confirm password must be match. Please check it");
        } else {
            RegisterProcess();
        }


    }// end checkValidation()------------


    /**
     * send manual user register request
     */
    public void RegisterProcess() {

       Utility.runProgressDialog(_activity);

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("name", _edName.getText().toString().trim());
        request.put("email", _edEmail.getText().toString().trim());
        request.put("mobile", _edPhone.getText().toString().trim());
        request.put("sex", "M");

        if (userType == 0) {
            request.put("user_type", "patient");
        } else {
            request.put("user_type", "doctor");
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();


        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getRegistrationData(request, new Callback<ExigencyModel>()

                {
                    @Override
                    public void failure(RetrofitError arg0) {

                        Utility.stopProgressDialog();
                        Toast.makeText(_activity, "" + arg0.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("failure " + arg0.toString());
                    }

                    @Override
                    public void success(ExigencyModel result, Response arg2) {
                        Utility.stopProgressDialog();
                        try {
                            if (result.getSuccess() == 1) {

                                Toast.makeText(_activity,""+result.getResult(),Toast.LENGTH_SHORT).show();


                                System.out.println("" + result.getResult());
                            } else {

                                Toast.makeText(_activity,"Please check filled information.",Toast.LENGTH_SHORT).show();
                                System.out.println("Please check filled information.");
                            }
                        } catch (Exception e) {

                            Toast.makeText(_activity,"Please check filled information.",Toast.LENGTH_SHORT).show();
                            System.out.println("Please check filled information.");
                        }


                    }
                }
        );
    }// end RegisterProcess()--------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }


    /**
     * Move to welcome page
     */
    private void callWelcomePage() {
        Utility.getUtilityInstance().doStartActivityWithFinish(_activity, WelcomeActivity.class, "right");
    }


    /**
     * Show message for short time
     */
    public void showToast(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }


}// end main class--------------------
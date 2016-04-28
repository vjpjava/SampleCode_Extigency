package com.exigency.exigencycare.exigencycareapp;

/**
 * Login screen, through facebook, googleplus, manual.
 *
 * @author GirnarSoft
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.LoginRegisterModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.rey.material.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    private Activity _activity;
    private Toolbar mToolbar;
    private EditText edEmail, edPassword;
    private CallbackManager callbackManager;
    private LinearLayout mllFb;
    private String mFBEmail = "", mFBName = "", mFBID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Call service for check facebook auth from server
                                        try {
                                            mFBID = "" + object.get("id");

                                            System.out.println("Your facebook is is-----" + mFBID);

                                            checkFBAuth(mFBID);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        setContentView(R.layout.login_activity);
        setBodyUI();
    }

    private void setBodyUI() {
        _activity = this;

        mllFb = (LinearLayout) findViewById(R.id.llFB);
        mllFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LoginManager.getInstance().logInWithReadPermissions(_activity, Arrays.asList("public_profile", "email", "user_friends"));
            }
        });


        getFbKeyHash("com.android.droozo.droozoapp");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mToolbar.findViewById(R.id.txtRight).setOnClickListener(this);

        findViewById(R.id.llGooglePlus).setOnClickListener(this);
        findViewById(R.id.txtForgetPassword).setOnClickListener(this);

        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);


        findViewById(R.id.llNewHere).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, SignUpActivity.class, "right");
            }
        });

        findViewById(R.id.llLogin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginFields();
            }
        });


        clickDoneButtonKeyboard();


    }// end setBodyUI()---------------


    public void getFbKeyHash(String packageName) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void clickDoneButtonKeyboard() {
        edPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    checkLoginFields();
                }
                return false;
            }
        });
    }

    /**
     * validation on Login page
     */
    private void checkLoginFields() {
        if (edEmail.getText().toString().trim().length() <= 0) {
            edEmail.setError("Email field shouldn't be blank");
        } else if (!Utility.isValidEmail(edEmail.getText().toString().trim())) {
            edEmail.setError("Please enter valid emailid!");
        } else if (edPassword.getText().toString().trim().length() <= 0) {
            edPassword.setError("Password field shouldn't be blank");
        } else if (edPassword.getText().toString().trim().length() <= 5 || edPassword.getText().toString().trim().length() >= 16) {
            edPassword.setError("Password should be between 6 to 15 characters");
        } else {
            Utility.runProgressDialog(_activity);
            LoginProcess();
            //callWelcomePage();
        }
    }// end checkLoginFields()----------------


    /**
     * click event on buttons
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRight:
                //checkLoginFields();
                break;
            case R.id.llGooglePlus:
                showToast("Google Plus");
                break;
            case R.id.txtForgetPassword:

                // Utility.doStartActivityWithoutFinish(_activity, FilterActivity.class, "right");

                popupForgotPassword();
                break;
            default:
                break;
        }

    }// end onClick()-----------------

    /**
     * Check fb id already register or not if register then go in otherwise go for register
     */
    public void checkFBAuth(String id) {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("fb_database_id", id.trim());
        request.put("service_uuidgen", CommonMethods.Signature);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.checkFBAuth(request, new Callback<LoginRegisterModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
            }

            @Override
            public void success(LoginRegisterModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();

                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                    showToast("" + data.getMessage());
                } else {
                    // saveDataInPrefernce(data);
                    /*Utility.SetPreferencesInteger(_activity, "USERID", data.getUserid());
                    Utility.SetPreferences(_activity, "USERNAME", data.getFirstname());
                    Utility.SetPreferences(_activity, "USERIMAGE", data.getProfile_image());*/
                    callWelcomePage();
                }
            }
        });
    }


    private void saveDataInPrefernce(String token) {
        //Utility.SetPreferencesInteger(_activity, "USERID", data.getUserid());
        Utility.SetPreferences(_activity, "token", token);
        //Utility.SetPreferences(_activity, "USERIMAGE", data.getProfile_image());
    }


    /**
     * Login process, call api and further continue according to status code
     */
    public void LoginProcess() {



        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("email", edEmail.getText().toString().trim());
        request.put("password", edPassword.getText().toString().trim());


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.getLoginData(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("" + arg0.toString());
                Utility.stopProgressDialog();
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                Utility.stopProgressDialog();
                // check status code if 500 then show message otherwise continue
                try {
                    if (result.getSuccess() == 1) {
                        Utility.SetPreferencesInteger(_activity, "USERID", Integer.valueOf(result.getUser_details().getId()));
                        saveDataInPrefernce(result.getToken());
                        callWelcomePage();
                    } else {

                    }
                } catch (Exception e) {
                    System.out.println("Please try again for access.");
                }
            }
        });
    }

    /**
     * Move to welcome page
     */
    private void callWelcomePage() {
        Utility.getUtilityInstance().doStartActivityWithFinish(_activity, WelcomeActivity.class, "right");
    }

    /**
     * Forgot password
     */
    @SuppressLint("NewApi")
    public void popupForgotPassword() {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_forgot_password);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);
        dialogMapMain.getWindow().setDimAmount(0.50f);
        dialogMapMain.show();

        final EditText edEmail = (EditText) dialogMapMain.findViewById(R.id.edEmail);


        dialogMapMain.findViewById(R.id.llDone).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (!Utility.isValidEmail(edEmail.getText().toString().trim())) {
                            edEmail.setError("Please enter valid email!");
                        } else {
                            //forgotPasswordService(edEmail.getText().toString().trim());
                            dialogMapMain.dismiss();
                        }
                    }
                });


    }// end popup-----------------------

    /**
     * send forgot password request on server
     *
     * @param email
     */
    private void forgotPasswordService(String email) {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("email", email);
        request.put("service_uuidgen", CommonMethods.Signature);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.forgotPassword(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showToast("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();
                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                    showToast("" + data.getMessage());
                } else {
                    showToast("" + data.getMessage());
                }
            }
        });
    }// end forgotPasswordService()---------------


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


}// end main class----------------------

package com.android.droozo.droozoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.droozo.Api.ApiCaller;
import com.android.droozo.Model.LoginRegisterModel;
import com.android.droozo.Model.ResponseData;
import com.android.droozo.util.CircleImageView;
import com.android.droozo.util.CommonMethods;
import com.android.droozo.util.ImageOperationUtil;
import com.android.droozo.util.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class SignUpActivity extends AppCompatActivity implements
        OnClickListener {
    private Activity _activity;
    private EditText _edName, _edCategory, _edEmail, _edPassword,
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
                                        // check email is coming or not
                                        // if coming then direct otherwise prompt popup for email
                                        checkEmailFromFB(object);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        showToast("Login Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showToast(exception.getMessage());
                    }
                });

        setContentView(R.layout.signup_activity);

        toolbarMaintain();

        setBodyUI();
    }// end onCreate()--------------


    /**
     * check email is coming or not
     * if coming then direct otherwise prompt popup for email
     */
    private void checkEmailFromFB(JSONObject object) {

        try {
            if (object.has("email")) {
                fbRegisterProcess(object, "NOT");
            } else {
                popupEnterEmail(object);
            }
        } catch (Exception e) {

        }


    }// end checkEmailFromFB()-----------------


    /**
     * proceed toolbar according to page
     */
    private void toolbarMaintain() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.close);

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
        initializeView();
        typeOFsignUpUser();
        mllFb = (LinearLayout) findViewById(R.id.llFB);
        mllFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(_activity, Arrays.asList("public_profile", "email", "user_friends"));
            }
        });

        imageUtil = new ImageOperationUtil(_activity);

    }// end setBodyUI()-----------------

    /**
     * used for find all view
     */
    private void initializeView() {
        takeImg = (CircleImageView) findViewById(R.id.takeImg);

        _edName = (EditText) findViewById(R.id.edName);
        _edCategory = (EditText) findViewById(R.id.edCategory);
        _edEmail = (EditText) findViewById(R.id.edEmail);
        _edPassword = (EditText) findViewById(R.id.edPassword);
        _edConfirmPassword = (EditText) findViewById(R.id.edConPassword);
        _edPhone = (EditText) findViewById(R.id.edPhone);
        _tgButton = (ToggleButton) findViewById(R.id.togglebtn);
        // if consumer there then it will be hide otherwise visible
        _dividerCategory = (View) findViewById(R.id.dividerCategory);

        takeImg.setOnClickListener(this);

    }

    /**
     * used for check user type 0 mean Consumer otherwise Service Provider
     *
     * @author GirnarSoft
     */
    private void typeOFsignUpUser() {
        if (getIntent().getIntExtra("TYPE", 0) == 0) {
            _edCategory.setVisibility(View.GONE);
            _dividerCategory.setVisibility(View.GONE);
        } else {
            _edCategory.setVisibility(View.VISIBLE);
            _dividerCategory.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Creating folder
     */
    private void createFolder() {
        try {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "DroozoApp");
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception e) {
            Log.v("TAG", e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRight:
                RegisterProcess();

                //Utility.getUtilityInstance().doStartActivityWithFinish(_activity, WelcomeActivity.class, "right");
                break;
            case R.id.takeImg:
                popupSelectionForImage();
                break;

            default:
                break;
        }

    }// end onClick()-----------------

    /**
     * send manual user register request
     */
    public void RegisterProcess() {
        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("firstname", _edName.getText().toString().trim());
        request.put("lastname", _edName.getText().toString().trim());
        request.put("email", _edEmail.getText().toString().trim());
        request.put("phone", _edPhone.getText().toString().trim());
        request.put("password", _edConfirmPassword.getText().toString().trim());
        request.put("service_uuidgen", CommonMethods.Signature);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();


        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getRegistrationData(request, new Callback<LoginRegisterModel>()

                {
                    @Override
                    public void failure(RetrofitError arg0) {
                        Toast.makeText(_activity, "failure " + arg0.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("failure " + arg0.toString());
                    }

                    @Override
                    public void success(LoginRegisterModel result, Response arg2) {
                        ResponseData data;
                        data = result.getResponseData();
                        userid = data.getUserid();
                        Utility.SetPreferencesInteger(_activity, "USERID", data.getUserid());
                        Utility.SetPreferences(_activity, "USERNAME", data.getFirstname());

                        renameFile(userid);
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
     * User can select from gallery and take image
     */
    @SuppressLint("NewApi")
    public void popupSelectionForImage() {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_add_more_image);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);

        dialogMapMain.getWindow().setDimAmount(0.50f);

        dialogMapMain.show();

        dialogMapMain.findViewById(R.id.llcamera).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Uri opURI = Uri.fromFile(new File(
                                imageUtil.CAPTURED_IMAGE_PATH));

                        Intent mIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, opURI);
                        mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

                        File camFile = new File(imageUtil.CAPTURED_IMAGE_PATH);
                        if (camFile.exists()) {
                            camFile.delete();
                        }

                        startActivityForResult(mIntent, RESULT_CAMERA);
                        dialogMapMain.dismiss();
                    }
                });

        dialogMapMain.findViewById(R.id.llGallery).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent gallery = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, RESULT_GALLERY);
                        dialogMapMain.dismiss();
                    }
                });

        dialogMapMain.findViewById(R.id.llCancel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialogMapMain.dismiss();
                    }
                });
    }// end popup-----------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CAMERA) {

            try {
                File camFile = new File(imageUtil.CAPTURED_IMAGE_PATH);
                if (camFile.exists()) {
                    updateUserImage(camFile.getPath());
                }

            } catch (Exception e) {

                Log.e("In onActivityResult", "RESULT_CAMERA " + e.toString());
            }
        } else if (requestCode == RESULT_GALLERY) {

            try {

                Uri selectedimage = data.getData();
                String[] filepathcolumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedimage,
                        filepathcolumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filepathcolumn[0]);
                String filepath = cursor.getString(columnIndex);
                cursor.close();

                String imagePath = Environment.getExternalStorageDirectory() + File.separator + "DroozoApp" + File.separator + "123.png";

                System.out.println("your image path is-----" + imagePath);

                File f = new File(imagePath);
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                        copyFile(new File(filepath), f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                updateUserImage(filepath);
            } catch (Exception e) {
            }
        }
    }


    /**
     * Copying file from source to destination
     *
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    private void copyFile(File sourceFile, File destFile) throws IOException {
        try {
            if (!sourceFile.exists()) {
                return;
            }

            FileChannel source = null;
            FileChannel destination = null;
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        } catch (Exception e) {
            Log.v("TAG", e.getMessage());
        }
    }


    /**
     * Renaming file
     */
    private void renameFile(int userid) {
        try {
            File sdcard = new File(Environment.getExternalStorageDirectory() + File.separator + "DroozoApp");
            File from = new File(sdcard, "123.png");
            File to = new File(sdcard, userid + ".png");
            from.renameTo(to);

            File newFile = new File(Environment.getExternalStorageDirectory() + File.separator + "DroozoApp" + File.separator + userid + ".png");

            TypedFile typedFile = new TypedFile("multipart/form-data", newFile);
            uploadImage(typedFile);
        } catch (Exception e) {
            Log.v("TAG", e.getMessage());
        }
    }// end renameFile()------------------

    public void uploadImage(TypedFile file) {
        System.out.println("failure " + file);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.uploadImage(file, userid, "E662A741-0B91-4E04-BFC0-F0D8EFA608B0", new Callback<LoginRegisterModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Toast.makeText(_activity, "failure " + arg0.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(LoginRegisterModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();

                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                    showToast("" + data.getMessage());
                } else {
                    Utility.SetPreferencesInteger(_activity, "USERID", data.getUserid());
                    Utility.SetPreferences(_activity, "USERIMAGE", data.getProfile_image());
                    callWelcomePage();
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

    private void updateUserImage(String filepath) {
        mFilePath = filepath;
        mUserBitmap = imageUtil.getBitmapFromPath(filepath);
        takeImg.setImageBitmap(mUserBitmap);
    }

    /**
     * Show message for short time
     */
    public void showToast(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * for Register fb user in our app
     * <p/>
     * get object(profile info) from facebook
     */
    private void fbRegisterProcess(JSONObject object, String email) {
        try {
            mFBID = "" + object.get("id");
            mFBName = "" + object.get("name");
            if (email.equalsIgnoreCase("NOT")) {
                try {
                    mFBEmail = "" + object.get("email");
                } catch (Exception e) {

                }
            } else {
                mFBEmail = email;
            }


            new SaveImage().execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }// end fbRegisterProcess()-----------------


    class SaveImage extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {

            mBitmapFB = getBitmapFromURL(("https://graph.facebook.com/" + mFBID + "/picture?type=large"));

            return null;
        }

        protected void onPostExecute(String feed) {
            fbRegisterProcess();
        }
    }


    private void savePathBitmap() {
        File file = _activity.getCacheDir();
        outputFile = null;
        try {
            outputFile = File.createTempFile(String.valueOf(userid), ".png", file);


        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
            mBitmapFB.compress(Bitmap.CompressFormat.PNG, 100,
                    fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    /**
     * send facebook user register request
     */
    public void fbRegisterProcess() {
        HashMap<String, String> request = new HashMap<String, String>();
        String signatureData, type;
        request.put("fb_database_id", mFBID.trim());
        request.put("firstname", mFBName.trim());
        request.put("lastname", mFBName.trim());

        try {
            request.put("email", mFBEmail.trim());
        } catch (Exception e) {
            request.put("email", "");
        }


        request.put("phone", "");
        request.put("password", "");
        request.put("service_uuidgen", CommonMethods.Signature);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();


        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getRegistrationData(request, new Callback<LoginRegisterModel>()

                {
                    @Override
                    public void failure(RetrofitError arg0) {
                        Toast.makeText(_activity, "failure " + arg0.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("failure " + arg0.toString());
                    }

                    @Override
                    public void success(LoginRegisterModel result, Response arg2) {
                        ResponseData data;
                        data = result.getResponseData();

                        if (result.getStatusCode() == 500) {
                            Toast.makeText(_activity, "failure in success" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            userid = data.getUserid();
                            Utility.SetPreferencesInteger(_activity, "USERID", userid);

                            savePathBitmap();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    savePathBitmap();
                                    if (mBitmapFB != null) {
                                        File file = new File(outputFile.getAbsolutePath());
                                        TypedFile typedFile = new TypedFile("multipart/form-data", file);
                                        uploadImage(typedFile);

                                    } else {

                                    }
                                }
                            });//end runOnUIThread-----------------
                        }
                    }//end success()------------
                }
        );
    }// end RegisterProcess()--------------


    /**
     * enter email
     */
    @SuppressLint("NewApi")
    public void popupEnterEmail(final JSONObject object) {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_forgot_password);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);
        dialogMapMain.getWindow().setDimAmount(0.50f);
        dialogMapMain.show();

        final EditText edEmail = (EditText) dialogMapMain.findViewById(R.id.edNameOther);

        final TextView txtForgotPassword = (TextView) dialogMapMain.findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setText("ENTER EMAILID");


        dialogMapMain.findViewById(R.id.llDone).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (!Utility.isValidEmail(edEmail.getText().toString().trim())) {
                            edEmail.setError("Please enter valid emailid!");
                        } else {
                            fbRegisterProcess(object, edEmail.getText().toString());
                            dialogMapMain.dismiss();
                        }
                    }
                });


    }// end popup-----------------------

}// end main class--------------------
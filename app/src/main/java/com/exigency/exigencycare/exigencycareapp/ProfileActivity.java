package com.exigency.exigencycare.exigencycareapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.LoginRegisterModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.util.CircleImageView;
import com.exigency.exigencycare.util.CircleTransform;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.ImageOperationUtil;
import com.exigency.exigencycare.util.Utility;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Show user profile
 * Created by  on 29/9/15.
 */
public class ProfileActivity extends AppCompatActivity {
    private View view;
    private Activity mActivity = this;
    private Toolbar mToolBar;
    private int userid;
    private final int RESULT_CAMERA = 100;
    private final int RESULT_GALLERY = 200;
    private CircleImageView imgUserDrawer;
    private TextView txtFirstName, txtLastName, txtEmailDrawer, txtPhone;
    private ImageOperationUtil imageUtil = null;
    private String mFilePath = null;
    private Bitmap mUserBitmap;
    private ResponseData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);

        toolBarManage();
        setBodyUI();

    }// end onCreate()-----------------


    @Override
    protected void onResume() {
        super.onResume();
        getProfileDetailsService();
    }

    /**
     * maintain Toolbar ui
     */
    private void toolBarManage() {
        mToolBar = (Toolbar) findViewById(R.id.llToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setNavigationIcon(R.drawable.close);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.green_bg));

        TextView txtHeader = (TextView) mToolBar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("PROFILE");
        TextView txtEdit = (TextView) mToolBar
                .findViewById(R.id.txtRight);
        txtEdit.setText("EDIT");
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("FNAME", data.getPersonal_detail().getFname().trim());
                bundle.putString("LNAME", data.getPersonal_detail().getLname().trim());
                bundle.putString("EMAIL", data.getPersonal_detail().getEmail().trim());
                bundle.putString("MOBILE", data.getPersonal_detail().getPhone().trim());

                Utility.doStartActivityWithoutFinishBundle(mActivity, EditProfileActivity.class, bundle, "right");
            }
        });
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }// end toolBarManage()-------------


    /**
     * intialize view and get profile info
     */
    private void setBodyUI() {
        userid = Utility.getPreferencesInteger(mActivity, "USERID");

        // initailze UI
        imgUserDrawer = (CircleImageView) findViewById(R.id.imgUserDrawer);
        txtFirstName = (TextView) findViewById(R.id.txtFirstName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtEmailDrawer = (TextView) findViewById(R.id.txtEmailDrawer);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        imageUtil = new ImageOperationUtil(mActivity);

        imgUserDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelectionForImage();
            }
        });


    }// end setBodyUI()----------------

    /**
     * User can select from gallery and take image
     */
    @SuppressLint("NewApi")
    public void popupSelectionForImage() {
        final Dialog dialogMapMain = new Dialog(mActivity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_add_more_image);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);

        dialogMapMain.getWindow().setDimAmount(0.50f);

        dialogMapMain.show();

        dialogMapMain.findViewById(R.id.llcamera).setOnClickListener(
                new View.OnClickListener() {

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
                new View.OnClickListener() {

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
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialogMapMain.dismiss();
                    }
                });
    }// end popup-----------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    private void updateUserImage(String filepath) {
        mFilePath = filepath;
        mUserBitmap = imageUtil.getBitmapFromPath(filepath);
        imgUserDrawer.setImageBitmap(mUserBitmap);
        renameFile(userid);


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
                Toast.makeText(mActivity, "failure " + arg0.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(LoginRegisterModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();

                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                } else {

                    Utility.SetPreferencesInteger(mActivity, "USERID", data.getUserid());
                    Utility.SetPreferences(mActivity, "USERIMAGE", data.getProfile_image());

                }

            }
        });
    }//end uploadImage()----------------


    /**
     * generate request and get data from server
     */
    private void getProfileDetailsService() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("userid", String.valueOf(userid));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getProfileDetails(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.showMessage("failure " + arg0.toString(), mActivity);
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                data = result.getResponseData();

                if (result.getStatusCode() == 500) {
                } else {
                    setProfileInfo(data);
                }
            }
        });
    }// end getProfileDetailsService()----------------


    /**
     * set profile info on corresponding UI
     */
    private void setProfileInfo(ResponseData data) {

        Picasso.with(mActivity).load(data.getPersonal_detail().getProfile_image()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).into(imgUserDrawer);
        txtFirstName.setText("" + data.getPersonal_detail().getFname());
        txtLastName.setText("" + data.getPersonal_detail().getLname());
        txtEmailDrawer.setText("" + data.getPersonal_detail().getEmail());
        txtPhone.setText("" + data.getPersonal_detail().getPhone());

    }// end setProfileInfo()-----------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(mActivity, "left");
    }

}// end main class--------------

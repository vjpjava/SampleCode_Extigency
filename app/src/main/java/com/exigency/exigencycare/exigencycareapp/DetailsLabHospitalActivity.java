package com.exigency.exigencycare.exigencycareapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.Doctors;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.Labs;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.fragment.DatePickerFragment;
import com.exigency.exigencycare.interfaces.UpdateDateTimeInterface;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * detail of doctor
 * Created by  on 29/9/15.
 */
public class DetailsLabHospitalActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Activity _activity = this;
    private Labs data;
    private TextView txtNameDetails, txtAbout, txtProfileTag, txtService, txtAddressDetails;
    private TextView txtDes, txtFirstDateTime, txtSecondDateTime;
    private ImageView imgUserDrawer, imgResetDateTimeFirst, imgResetDateTimeSecond;
    private int user_id;
    // 0 mean first date and time otherwise second date time
    public static int sIntWhichOne = 0;

    public String otherName = "", otherMobileNo = "", otherEmail = "";
    public LinearLayout llSelf, llOthers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_labs_hospital_activity);
        user_id = Utility.getPreferencesInteger(_activity, "USERID");
        setData();

        setBodyUI();
        getCurrentDateAndTime();
    }// end onCreate()----------------


    private void setData() {

        imgUserDrawer = (ImageView) findViewById(R.id.imgUserDrawer);
        txtNameDetails = (TextView) findViewById(R.id.txtNameDetails);
        txtAbout = (TextView) findViewById(R.id.txtAbout);
        txtProfileTag = (TextView) findViewById(R.id.txtProfileTag);
        txtService = (TextView) findViewById(R.id.txtService);
        txtDes = (TextView) findViewById(R.id.txtDes);
        txtAddressDetails = (TextView) findViewById(R.id.txtAddressDetails);
        txtFirstDateTime = (TextView) findViewById(R.id.txtFirstDateTime);


        // imgUserDrawer.setBackgroundResource(R.drawable.default_thumb);

        data = (Labs) getIntent().getSerializableExtra("OBJECT");
        txtNameDetails.setText(data.getName());

        txtAbout.setText(data.getAbout());
        txtProfileTag.setText(data.getProfile_tag_line());
        txtService.setText(data.getServices());
        txtDes.setText(data.getProfile_small_description());


        txtAddressDetails.setText(data.getArea() + ", " + data.getAddress());

    }// end setData()---------------


    private void getCurrentDateAndTime() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String delegate = "hh:mm aaa";
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        //txtFirstDateTime.setText(formattedDate + " " + (String) DateFormat.format(delegate, Calendar.getInstance().getTime()));

    }


    public void setBodyUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.close);
        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("SPECIALIST DETAILS");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.llMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinishStringValue(_activity, MapsActivity.class, "MAP", data.getAddress(), "right");
            }
        });



    }// end setBodyUI()-------------

    /**
     * used for book appointment process
     * <p/>
     * check login or not
     * check select terms and condition
     */
    private void bookAppointmentProcess() {
        if (user_id == 0) {
            showMsg("Please login to make appointment!");
        } else {
        }
    }// end bookAppointmentProcess()------------------

    /**
     * call when user click on Time and date option(FIRST)
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        sIntWhichOne = 0;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * call when user click on Time and date option(SECOND)
     *
     * @param v
     */
    public void showDatePickerDialogSecond(View v) {
        sIntWhichOne = 1;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    /**
     * User can select from gallery and take image
     */
    @SuppressLint("NewApi")
    public void popupOther() {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_other);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);
        dialogMapMain.getWindow().setDimAmount(0.50f);
        dialogMapMain.show();

        final EditText edName = (EditText) dialogMapMain.findViewById(R.id.edNameOther);
        final EditText edMno = (EditText) dialogMapMain.findViewById(R.id.edMobileNo);
        final EditText edEmail = (EditText) dialogMapMain.findViewById(R.id.edEmailOther);

        dialogMapMain.findViewById(R.id.llDone).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otherName = edName.getText().toString().trim();
                        otherMobileNo = edMno.getText().toString().trim();
                        otherEmail = edEmail.getText().toString().trim();
                        //check all field
                        if (otherName.length() > 0 && otherMobileNo.length() > 0 && otherEmail.length() > 0) {
                            dialogMapMain.dismiss();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    llSelf.setBackgroundResource(R.drawable.selector_btn_for_grey_grey);
                                    llOthers.setBackgroundResource(R.drawable.selector_btn_for_blue);
                                }
                            });

                        } else {
                            showMsg("Please fill all fields!");
                        }
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
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    private void showMsg(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }


}// end main class----------------

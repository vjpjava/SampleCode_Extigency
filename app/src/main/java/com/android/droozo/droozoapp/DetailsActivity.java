package com.android.droozo.droozoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.droozo.Api.ApiCaller;
import com.android.droozo.Model.Doclist;
import com.android.droozo.Model.HealthProviderModel;
import com.android.droozo.Model.ResponseData;
import com.android.droozo.adapter.SearchCityAdapter;
import com.android.droozo.fragment.DatePickerFragment;
import com.android.droozo.interfaces.UpdateDateTimeInterface;
import com.android.droozo.util.CircleTransform;
import com.android.droozo.util.CommonMethods;
import com.android.droozo.util.Utility;
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
 * Created by GSS-Vishnu Kant on 29/9/15.
 */
public class DetailsActivity extends AppCompatActivity implements UpdateDateTimeInterface {

    private Toolbar mToolbar;
    private Activity _activity = this;
    private Doclist data;
    private TextView txtNameDetails, txtQual, txtQualification, txtRate, txtAddressDetails;
    private TextView txtTime, txtRecommend, txtFirstDateTime, txtSecondDateTime;
    private ImageView imgUserDrawer, imgResetDateTimeFirst, imgResetDateTimeSecond;
    private int user_id;
    private ToggleButton mToggleBtnTerms;
    public static String sStrForFirstDate = "";
    public static String sStrForFirstTime = "";
    public static String sStrForSecondDate = "";
    public static String sStrForSecondTime = "";
    // 0 mean first date and time otherwise second date time
    public static int sIntWhichOne = 0;

    public String otherName = "", otherMobileNo = "", otherEmail = "";
    public LinearLayout llSelf, llOthers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        user_id = Utility.getPreferencesInteger(_activity, "USERID");
        setData();

        setBodyUI();
        getCurrentDateAndTime();
    }// end onCreate()----------------

    private void setData() {
        data = (Doclist) getIntent().getSerializableExtra("OBJECT");

        mToggleBtnTerms = (ToggleButton) findViewById(R.id.togglebtnTerms);
        imgUserDrawer = (ImageView) findViewById(R.id.imgUserDrawer);
        imgResetDateTimeFirst = (ImageView) findViewById(R.id.imgResetDateTimeFirst);
        imgResetDateTimeSecond = (ImageView) findViewById(R.id.imgResetDateTimeSecond);

        imgResetDateTimeFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sStrForFirstTime = "";
                sStrForFirstDate = "";
                txtFirstDateTime.setText("Please select date and time...");
            }
        });
        imgResetDateTimeSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sStrForSecondDate = "";
                sStrForSecondTime = "";
                txtSecondDateTime.setText("Please select date and time...");
            }
        });


        txtNameDetails = (TextView) findViewById(R.id.txtNameDetails);
        txtQual = (TextView) findViewById(R.id.txtQual);
        txtQualification = (TextView) findViewById(R.id.txtQualification);
        txtRate = (TextView) findViewById(R.id.txtRate);
        txtAddressDetails = (TextView) findViewById(R.id.txtAddressDetails);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtRecommend = (TextView) findViewById(R.id.txtRecommend);
        txtFirstDateTime = (TextView) findViewById(R.id.txtFirstDateTime);
        txtSecondDateTime = (TextView) findViewById(R.id.txtSecondDateTime);
        llSelf = (LinearLayout) findViewById(R.id.llSelf);
        llOthers = (LinearLayout) findViewById(R.id.llOthers);


        if (data.getPhoto().equalsIgnoreCase("")) {
            imgUserDrawer.setBackgroundResource(R.drawable.default_thumb);
        } else {
            Picasso.with(_activity).load(data.getPhoto()).transform(new CircleTransform()).into(imgUserDrawer);
        }
        txtNameDetails.setText(data.getDoctor_name());
        txtQual.setText(data.getQualification());
        txtQualification.setText(data.getQualification());
        txtRate.setText(data.getFee_Charged());
        txtAddressDetails.setText(data.getAddress());
        txtTime.setText(data.getDuty_timing());
        txtRecommend.setText(data.getRecommended());

        llSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSelf.setBackgroundResource(R.drawable.selector_btn_for_blue);
                llOthers.setBackgroundResource(R.drawable.selector_btn_for_grey_grey);
            }
        });

        llOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupOther();
            }
        });

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

        findViewById(R.id.llbtnAppointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointmentProcess();
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
            // check terms toggle on or not
            if (mToggleBtnTerms.isChecked()) {
                if (sStrForFirstDate.trim().length() > 0 && sStrForFirstTime.trim().length() > 0) {
                    callServiceForBookAppointment();
                } else {
                    showMsg("Please fill atleast one date and time option for appointment!");
                }
                //Toast.makeText(_activity, "Your request has been sent successfully to make appointment!", Toast.LENGTH_SHORT).show();
            } else {
                showMsg("Please turn on Terms of Use!");
            }
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

    @Override
    public void updateDateTime(String strdateTime) {
        // update date and time option with selected date and time
        // 0 mean first date and time updated otherwise second date Time
        if (DetailsActivity.sIntWhichOne == 0) {
            txtFirstDateTime.setText(sStrForFirstDate + " " + sStrForFirstTime);
        } else {
            txtSecondDateTime.setText(sStrForSecondDate + " " + sStrForSecondTime);
        }
    }// end updateDateTime()----------------------


    /**
     * book appointment for doctor
     */
    public void callServiceForBookAppointment() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("booked_for", String.valueOf(data.getId()));
        request.put("booked_by", String.valueOf(user_id));
        request.put("booking_type", "DC");
        request.put("date_option1", sStrForFirstDate);
        request.put("time_option1", sStrForFirstTime);
        request.put("date_option2", sStrForSecondDate);
        request.put("time_option2", sStrForSecondTime);
        request.put("hiddenslot", "yes");
        request.put("self", "Y");
        request.put("other_name", otherName);
        request.put("other_mobile", otherMobileNo);
        request.put("other_email", otherEmail);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API_BOOK).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.createBookingProcess(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                showMsg("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                ResponseData data = result.getResponseData();

                if (result.getStatusCode() == 500) {
                    showMsg("" + data.getMessage());
                } else {
                    showMsg("" + data.getMessage());
                    onBackPressed();
                }
            }
        });
    }// end HealthProviderProcess()-----------------

}// end main class----------------

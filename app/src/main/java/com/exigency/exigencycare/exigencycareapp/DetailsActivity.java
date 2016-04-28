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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.Doctors;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.fragment.DatePickerFragment;
import com.exigency.exigencycare.interfaces.UpdateDateTimeInterface;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class DetailsActivity extends AppCompatActivity implements UpdateDateTimeInterface {

    private Toolbar mToolbar;
    private Activity _activity = this;
    private TextView txtNameDetails, txtQual, txtQualification, txtRate, txtAddressDetails;
    private TextView txtTime, txtFirstDateTime, txtSecondDateTime;
    private ImageView imgUserDrawer, imgResetDateTimeFirst, imgResetDateTimeSecond;
    private int user_id;
    public static String sStrForFirstDate = "";
    public static String sStrForFirstTime = "";
    public static String sStrForSecondDate = "";
    public static String sStrForSecondTime = "";
    // 0 mean first date and time otherwise second date time
    public static int sIntWhichOne = 0;

    public String otherName = "", otherMobileNo = "", otherEmail = "";
    public LinearLayout llSelf, llOthers;
    public Doctors data;
    Spinner sp, spTime;

    public String drID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        user_id = Utility.getPreferencesInteger(_activity, "USERID");
        setData();

        setBodyUI();
        getCurrentDateAndTime();

        if (Utility.getUtilityInstance().getConnectivityStatus(_activity)) {
            Utility.runProgressDialog(_activity);
            CallServiceForGetTimeSlot(drID);
        } else {
            Toast.makeText(_activity, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }


    }// end onCreate()----------------


    private void setData() {


        sp = (Spinner) findViewById(R.id.spClinicName);
        spTime = (Spinner) findViewById(R.id.spTime);


        imgUserDrawer = (ImageView) findViewById(R.id.imgUserDrawer);
        txtNameDetails = (TextView) findViewById(R.id.txtNameDetails);
        txtQualification = (TextView) findViewById(R.id.txtQualification);
        txtRate = (TextView) findViewById(R.id.txtRate);
        txtAddressDetails = (TextView) findViewById(R.id.txtAddressDetails);
        txtFirstDateTime = (TextView) findViewById(R.id.txtFirstDateTime);


        // imgUserDrawer.setBackgroundResource(R.drawable.default_thumb);
        data = (Doctors) getIntent().getSerializableExtra("OBJECT");

        drID = data.getId();

        txtNameDetails.setText("" + data.getName());
        txtQualification.setText("" + data.getEducation());
        txtRate.setText("" + data.getFee());
        txtAddressDetails.setText("" + data.getArea() + ", " + data.getAddress());

        Picasso.with(_activity).load("http://exigencycare.com/uploads/User%20Images/" + data.getUser_image()).placeholder(R.drawable.default_thumb).into(imgUserDrawer);


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
                if (Utility.getPreferencesInteger(_activity, "USERID") == 0) {
                    Utility.showMessage("Please login or register for make appointment!", _activity);
                } else {
                    Utility.runProgressDialog(_activity);
                    callServiceForBookAppointment();
                }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    private void showMsg(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDateTime(String strdateTime, String reqselectedDate) {
        // update date and time option with selected date and time
        // 0 mean first date and time updated otherwise second date Time
        if (DetailsActivity.sIntWhichOne == 0) {
            txtFirstDateTime.setText(reqselectedDate);
        }
    }// end updateDateTime()----------------------


    /**
     * book appointment for doctor
     */
    public void callServiceForBookAppointment() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("token", Utility.getPreferences(_activity, "token"));
        request.put("booking_date", txtFirstDateTime.getText().toString().trim());
        request.put("time", spTime.getSelectedItem().toString());
        request.put("select_appointment", "0");
        request.put("reason", "Reason");
        request.put("doctor_id", data.getId());
        request.put("user_id", String.valueOf(Utility.getPreferencesInteger(_activity, "USERID")));
        request.put("name", data.getName());
        request.put("email", data.getEmail());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.bookappointment(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                Utility.stopProgressDialog();
                Toast.makeText(_activity, "Your appointment has been created successfully!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }// end HealthProviderProcess()-----------------


    /**
     * send request for get country code
     */
    public void CallServiceForGetTimeSlot(String doctorID) {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("doctor_user_id", doctorID);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();


        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.doctortiminghome(request, new Callback<ExigencyModel>()

                {
                    @Override
                    public void failure(RetrofitError arg0) {
                        Utility.stopProgressDialog();
                    }

                    @Override
                    public void success(ExigencyModel result, Response arg2) {

                        ArrayList<String> arr = new ArrayList<String>();
                        ArrayList<String> arrName = new ArrayList<String>();
                        String str = "";
                        try {

                            for (int i = 0; i < result.getTimeslot().size(); i++) {
                                arr.add(result.getTimeslot().get(i).getTime());
                                str = result.getTimeslot().get(i).getClinic_name();
                            }
                            arrName.add(str);
                        } catch (Exception e) {
                        }


                        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(_activity, R.layout.custom_text, arrName);
                        ArrayAdapter<String> adapterTIme = new ArrayAdapter<String>(_activity, R.layout.custom_text, arr);
                        sp.setAdapter(adapterName);
                        spTime.setAdapter(adapterTIme);

                        Utility.stopProgressDialog();
                    }
                }
        );
    }// end RegisterProcess()--------------


}// end main class----------------

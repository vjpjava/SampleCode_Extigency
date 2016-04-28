package com.exigency.exigencycare.exigencycareapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.SelectedLinearLayout;
import com.exigency.exigencycare.util.Utility;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

/**
 * Created by GSS on 13/10/15.
 */
public class FilterSearchHealthActivity extends AppCompatActivity implements OnClickListener {

    private Activity mActivity;
    private SelectedLinearLayout mSelectedll;
    private Toolbar mToolBar;
    private FragmentManager fragmentManager;
    CommonMethods commonMethods;
    private TextView txtTimeInterval, txtPriceInterval;
    private String mSelectedDay = "Monday";
    private String mSelectedFee = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_search_health_activity);
        mActivity = this;

        toolBarManage();
        dayClick();
        setRangeBar();
        setRangeBarPrice();


    }// end onCreate()----------------

    /**
     * Manage toolbar left, title and right & click events
     */
    private void toolBarManage() {
        mToolBar = (Toolbar) findViewById(R.id.llToolbar);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.green_bg));
        mToolBar.setNavigationIcon(R.drawable.back);
        mToolBar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView txtHeader = (TextView) mToolBar.findViewById(R.id.toolbar_title);
        txtHeader.setText("FILTERS");

        TextView txtReset = (TextView) mToolBar.findViewById(R.id.txtRight);
        txtReset.setText("APPLY");
        txtReset.setOnClickListener(this);
    }


    /**
     * day filter option click
     */
    private void dayClick() {
        // by default categories tab open
        mSelectedll = (SelectedLinearLayout) findViewById(R.id.root);
        mSelectedll.setselected(0);

        findViewById(R.id.llMON).setOnClickListener(this);
        findViewById(R.id.llTUE).setOnClickListener(this);
        findViewById(R.id.llWED).setOnClickListener(this);
        findViewById(R.id.llTHU).setOnClickListener(this);
        findViewById(R.id.llFRI).setOnClickListener(this);
        findViewById(R.id.llSAT).setOnClickListener(this);
        findViewById(R.id.llSUN).setOnClickListener(this);
    }// end dayClick()--------------

    private void setRangeBar() {
        txtTimeInterval = (TextView) findViewById(R.id.txtTimeInterval);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        rangeSeekBar.setRangeValues(0, 24 * 4);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer integer1) {
                String firstExtension = "AM";
                String secondExtension = "PM";
                int hours = integer / 4;
                if (hours == 0) {
                    hours = 12;
                }
                // it will return hours.
                int minutes = (integer % 4) * 15;
                if (minutes == 0) {
                    minutes = 00;
                }
                // here will be minutes.
                if (hours > 12) {
                    hours = hours - 12;
                    firstExtension = "PM";
                } else {
                    firstExtension = "AM";
                }

                int hours2 = integer1 / 4; // it will return hours.
                int minutes2 = (integer1 % 4) * 15; // here will be minutes.
                if (minutes2 == 0) {
                    minutes2 = 00;
                }
                if (hours2 > 12) {
                    hours2 = hours2 - 12;
                    secondExtension = "PM";
                } else {
                    secondExtension = "AM";
                }

                String finalTimeInterval = hours + ":" + minutes + " " + firstExtension + " - " + hours2 + ":" + minutes2 + " " + secondExtension;
                txtTimeInterval.setText(finalTimeInterval);

            }
        });

        // Add to layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);
    }

    private void setRangeBarPrice() {

        txtPriceInterval = (TextView) findViewById(R.id.txtPriceInterval);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        rangeSeekBar.setRangeValues(0, 500);
        rangeSeekBar.setSelectedMinValue(75);
        rangeSeekBar.setSelectedMaxValue(300);
        txtPriceInterval.setText("Rs. " + 75 + " - Rs." + 300);
        mSelectedFee = 75 + "to" + 300;

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer integer1) {
                String finalTimeInterval = "Rs. " + integer + " - Rs." + integer1;
                txtPriceInterval.setText(finalTimeInterval);
                mSelectedFee = integer + "to" + integer1;

            }
        });

        // Add to layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder2);
        layout.addView(rangeSeekBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMON:
                mSelectedDay = "Monday";
                mSelectedll.setselected(0);
                break;
            case R.id.llTUE:
                mSelectedDay = "Tuesday";
                mSelectedll.setselected(1);
                break;
            case R.id.llWED:
                mSelectedDay = "Wednesday";
                mSelectedll.setselected(2);
                break;
            case R.id.llTHU:
                mSelectedDay = "Thursday";
                mSelectedll.setselected(3);
                break;
            case R.id.llFRI:
                mSelectedDay = "Friday";
                mSelectedll.setselected(4);
                break;
            case R.id.llSAT:
                mSelectedDay = "Saturday";
                mSelectedll.setselected(5);
                break;
            case R.id.llSUN:
                mSelectedDay = "Sunday";
                mSelectedll.setselected(6);
                break;


            case R.id.txtRight:
                //_activity, FilterSearchHealthActivity.class, "SPECIAL", WhichCategoryDoctorShow, "CITY", "", "DEFAULT", "XYZ", "right");

                // Utility.doStartActivityWithoutFinishString2Value(mActivity, FilterSearchListActivity.class, "TYPE", Utility.getStringExtraProcess("SPECIAL", mActivity), "CITY", Utility.getStringExtraProcess("CITY", mActivity), "right");

                Bundle bundle = new Bundle();
                bundle.putString("SPECIAL", Utility.getStringExtraProcess("SPECIAL", mActivity));
                bundle.putString("CITY", Utility.getStringExtraProcess("CITY", mActivity));
                bundle.putString("TIME", txtTimeInterval.getText().toString().trim());
                bundle.putString("DAY", mSelectedDay);
                bundle.putString("FEE", mSelectedFee);
                Utility.doStartActivityWithoutFinishBundle(mActivity, FilterSearchListActivity.class, bundle, "right");

                break;

            default:
                break;
        }
    }// end onClick()--------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(mActivity, "left");
    }
}//end main class---------------

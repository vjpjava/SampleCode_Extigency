package com.android.droozo.droozoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.fragment_filter.CategoryFilterFragment;
import com.android.droozo.fragment_filter.ManufacturerFilterFragment;
import com.android.droozo.fragment_slider.ContactUsFragment;
import com.android.droozo.fragment_slider.MyTransactionFragment;
import com.android.droozo.util.CommonMethods;
import com.android.droozo.util.SelectedLinearLayout;

/**
 * Created by GSS on 13/10/15.
 */
public class FilterActivity extends AppCompatActivity implements OnClickListener {

    private Activity mActivity;
    private SelectedLinearLayout mSelectedll;
    private Toolbar mToolBar;
    private FragmentManager fragmentManager;
    CommonMethods commonMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);
        mActivity = this;

        toolBarManage();
        leftMenuUIClick();
        setBodyUI();

    }// end onCreate()----------------

    /**
     * Manage toolbar left, title and right & click events
     */
    private void toolBarManage() {
        mToolBar = (Toolbar) findViewById(R.id.llToolbar);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        txtReset.setText("RESET");
        txtReset.setOnClickListener(this);
    }


    /**
     * left filter option click and redirect
     */
    private void leftMenuUIClick() {
        // by default categories tab open
        mSelectedll = (SelectedLinearLayout) findViewById(R.id.root);
        mSelectedll.setselected(0);

        findViewById(R.id.ll_Price).setOnClickListener(this);
        findViewById(R.id.ll_weight).setOnClickListener(this);
        findViewById(R.id.ll_category).setOnClickListener(this);
        findViewById(R.id.ll_manufacturer).setOnClickListener(this);
    }

    private void setBodyUI() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new ContactUsFragment()).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_weight:
                mSelectedll.setselected(4);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ManufacturerFilterFragment()).commit();
                break;
            case R.id.ll_category:
                mSelectedll.setselected(0);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new CategoryFilterFragment()).commit();
                break;
            case R.id.ll_manufacturer:
                mSelectedll.setselected(2);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ContactUsFragment()).commit();
                break;
            case R.id.ll_Price:
                mSelectedll.setselected(6);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MyTransactionFragment()).commit();
                break;
            case R.id.txtRight:
                Toast.makeText(mActivity, "Reset", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }// end onClick()--------------


}//end main class---------------

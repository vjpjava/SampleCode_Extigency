package com.android.droozo.droozoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.droozo.util.CircleImageView;
import com.android.droozo.util.ImageOperationUtil;
import com.android.droozo.util.Utility;

import java.io.File;

public class AddMoreShippingAddressActivity extends AppCompatActivity implements
        OnClickListener {
    private Activity mActivity;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmore_address_activity);
        mActivity = this;
        toolBarControl();
        initializeView();
        setBodyUI();
    }

    private void toolBarControl() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.blueBG));
        mToolbar.setNavigationIcon(R.drawable.back);

        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("SHIPPING ADDRESS");

        TextView txtRight = (TextView) mToolbar
                .findViewById(R.id.txtRight);
        txtRight.setTextColor(getResources().getColor(R.color.white));
        txtRight.setText("SAVE");

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "New Address added successfully.", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setBodyUI() {

    }// end setBodyUI()-----------------

    /**
     * used for find all view
     */
    private void initializeView() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

    }// end onClick()-----------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(mActivity, "left");
    }


}// end main class--------------------
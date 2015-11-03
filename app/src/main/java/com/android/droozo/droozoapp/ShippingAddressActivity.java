package com.android.droozo.droozoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.util.Utility;

/**
 * Created by GSS- Vishnu Kant on 8/10/15.
 */
public class ShippingAddressActivity extends AppCompatActivity {
    private Activity _activity;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address);

        setBodyUI();

    }

    public void setBodyUI() {
        _activity = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.blueBG));
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("SELECT SHIPPING ADDRESS");


        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        findViewById(R.id.rlAddmoreAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, AddMoreShippingAddressActivity.class, "right");
            }
        });
        findViewById(R.id.llCheckout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, PaymentCartActivity.class, "right");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.doAnim(_activity, "left");
    }
}

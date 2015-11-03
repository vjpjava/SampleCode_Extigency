package com.android.droozo.droozoapp;

/**
 * Search List screen where user search doctor
 *
 * @author GSS- Vishnu Kant
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.adapter.MyCartItemAdapter;
import com.android.droozo.adapter.SearchItemAdapter;
import com.android.droozo.util.Utility;

public class MyCartActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener*/ {
    private Toolbar mToolbar;
    private Activity _activity = this;
    private TextView txtHeader;
    private ImageView imgfilter;
    private ListView lvSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list_activity);
        setBodyUI();
    }


    public void setBodyUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.blueBG));
        txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("MY CART");

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lvSearchList = (ListView) findViewById(R.id.lvSearchItem);
        lvSearchList.setAdapter(new MyCartItemAdapter(_activity));


        findViewById(R.id.llbottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, ShippingAddressActivity.class, "right");
            }
        });

        // lvSearchList.setOnItemClickListener(this);

    }// end setBodyUI---------------------

    //@Override
    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    // Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, SingleItemActivity.class, "right");
    // }

    /**
     * filter search results
     * Vishnu Kant
     */
    private void filterSearchItem() {
        showMessage("Filter");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    public void showMessage(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();

    }

}// end main class--------------------
package com.android.droozo.droozoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.interfaces.UpdateTitleFragment;
import com.android.droozo.util.CircleImageView;
import com.android.droozo.util.ImageOperationUtil;
import com.android.droozo.util.Utility;

import java.io.File;

/**
 * Edit user profile
 * Created by GSS-Vishnu Kant on 29/9/15.
 */
public class EditProfileActivity extends AppCompatActivity {
    private View view;
    private Toolbar mToolBar;
    private Activity mActivty = this;
    private EditText edFirstName, edLastName, edEmailId, edContactNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_fragment);
        toolBarManage();
        setBodyUI();

    }

    private void toolBarManage() {
        mToolBar = (Toolbar) findViewById(R.id.llToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setNavigationIcon(R.drawable.back);

        TextView txtHeader = (TextView) mToolBar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("EDIT PROFILE");
        TextView txtSave = (TextView) mToolBar
                .findViewById(R.id.txtRight);
        txtSave.setText("SAVE");

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
                //Toast.makeText(EditProfileActivity.this, "Profile successfully updated!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });
    }// end toolBarManage()---------------------


    private void setBodyUI() {
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edLastName = (EditText) findViewById(R.id.edLastName);
        edEmailId = (EditText) findViewById(R.id.edEmailId);
        edContactNo = (EditText) findViewById(R.id.edContactNO);

    }// end setBodyUI()---------------------


    /**
     * check validation
     */

    private void checkValidation() {
        if (edFirstName.getText().toString().equalsIgnoreCase("")) {
            edFirstName.setError("Please enter First Name!");
        } else if (edLastName.getText().toString().equalsIgnoreCase("")) {
            edLastName.setError("Please enter Last Name!");
        } else if (edEmailId.getText().toString().equalsIgnoreCase("")) {
            edEmailId.setError("Please enter Email Id!");
        } else if (!Utility.isValidEmail(edEmailId.getText().toString())) {
            edEmailId.setError("Please enter valid sEmail Id!");
        } else if (edContactNo.getText().toString().equalsIgnoreCase("")) {
            edContactNo.setError("Please enter Contact No!");
        } else {
            updateProfileInfo();
        }

    }// end checkValidation()-----------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(mActivty, "left");
    }

}
package com.exigency.exigencycare.exigencycareapp;

/**
 * Login screen, through facebook, googleplus, manual.
 *
 * @author GirnarSoft
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.exigency.exigencycare.util.Utility;

public class LoginRegistrationActivity extends AppCompatActivity {
    private Activity _activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
        setBodyUI();
    }

    private void setBodyUI() {
        _activity = this;
        findViewById(R.id.signInUpSkiptxt).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, WelcomeActivity.class, "right");
                    }
                });

        findViewById(R.id.llSignin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, LoginActivity.class, "right");
            }
        });

        findViewById(R.id.llSignup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, SignUpActivity.class, "right");
            }
        });

    }// end setBodyUI()---------------


    /**
     * Show message for short time
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();
    }


}// end main class----------------------

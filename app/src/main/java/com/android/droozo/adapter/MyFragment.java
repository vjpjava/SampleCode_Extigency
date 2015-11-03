package com.android.droozo.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.droozo.droozoapp.LoginActivity;
import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SignUpActivity;
import com.android.droozo.droozoapp.WelcomeActivity;
import com.android.droozo.util.Utility;


public class MyFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private int mIndex;
    View v = null;

    public MyFragment() {

    }

    MyFragment(int index) {
        mIndex = index;
    }

    public static final MyFragment newInstance(String message, int index) {
        MyFragment f = new MyFragment(index);
        Bundle bdl = new Bundle(index);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        if (mIndex == 4) {
            v = inflater.inflate(R.layout.last_intro_signin_up_activity,
                    container, false);
            setBodyUI();
        } else {
            v = inflater.inflate(R.layout.first_intro_activity, container,
                    false);
            changeImageHIW(v, mIndex);
        }

        return v;

    }

    /**
     * Change background image on How It Work page
     *
     * @param v
     * @param pos
     */
    private void changeImageHIW(View v, int pos) {
        ImageView img = (ImageView) v.findViewById(R.id.introImg);
        TextView txtFirst = (TextView) v.findViewById(R.id.txtFirst);
        TextView txtSecond = (TextView) v.findViewById(R.id.txtSecond);
        if (pos == 1) {
            img.setImageResource(R.drawable.hiw_1);
            txtFirst.setText("Search from 20,000 + listed health providers in your city.");
            txtSecond.setText("And get instant appointment with doctors.\n Happy Health!");
        } else if (pos == 2) {
            img.setImageResource(R.drawable.hiw_2);
            txtFirst.setText("Your Health and Fitness shopping made easy.");
            txtSecond.setText("Buy authentic products securely.\n Happy Health!");
        } else {
            img.setImageResource(R.drawable.hiw_3);
            txtFirst.setText("Download our Health and Fitness Apps to make your fitness fun.");
            txtSecond.setText("Happy Health!");
        }
    }

    /**
     * Inital click event and manage UI
     */
    private void setBodyUI() {

        v.findViewById(R.id.signInUpSkiptxt).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Utility.getUtilityInstance().doStartActivityWithoutFinish(getActivity(), WelcomeActivity.class, "right");
                    }
                });

        v.findViewById(R.id.llSignin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getUtilityInstance().doStartActivityWithoutFinish(getActivity(), LoginActivity.class, "right");
            }
        });

        v.findViewById(R.id.llSignup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSignupSelection();
            }
        });

    }// end setBodyUI()-----------


    /**
     * used for select User type (Consumer / Service Provider)
     */
    @SuppressLint("NewApi")
    protected void popupSignupSelection() {
        final Dialog dialogMapMain = new Dialog(getActivity());
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_sign_up);
        dialogMapMain.getWindow().setGravity(Gravity.BOTTOM);
        dialogMapMain.setCancelable(true);

        dialogMapMain.getWindow().setDimAmount(0.50f);

        dialogMapMain.show();

        dialogMapMain.findViewById(R.id.llConsumer).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialogMapMain.dismiss();
                        Utility.getUtilityInstance().doStartActivityWithoutFinishIntValue(getActivity(), SignUpActivity.class, "TYPE", 0, "right");
                        // 0 mean Consumer
                    }
                });

        dialogMapMain.findViewById(R.id.llServiceProvider).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialogMapMain.dismiss();
                        Utility.getUtilityInstance().doStartActivityWithoutFinishIntValue(getActivity(), SignUpActivity.class, "TYPE", 1, "right");
                        // 1 mean Service Provider
                    }
                });

        dialogMapMain.findViewById(R.id.llCancel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialogMapMain.dismiss();

                    }
                });

    }//end popupSignupSelection----------------------

}// end main class-------------------------

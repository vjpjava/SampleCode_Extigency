package com.android.droozo.fragment_slider;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SearchHealthProviderActivity;
import com.android.droozo.fragment.HealthMartDashBoardFragment;
import com.android.droozo.interfaces.MoveFragment;
import com.android.droozo.util.Utility;

public class WelcomeFragment extends Fragment {
    private View view;
    private Activity _activity;


    public static WelcomeFragment newInstance(Activity act) {

        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    public WelcomeFragment() {
    }


    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //if (view == null) {

        view = inflater
                .inflate(R.layout.welcome_fragment, container, false);

        setBodyUI();
        // } else {
        //  if (view.getParent() != null) {
        //     ((ViewGroup) view.getParent()).removeView(view);
        // }
        // }
        // add grid header

        return view;
    }

    private void setBodyUI() {
        _activity = getActivity();
        _activity.findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.green_bg));
        TextView txt = (TextView) _activity.findViewById(R.id.toolbar_title);
        txt.setText("WELCOME");


        view.findViewById(R.id.llSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getUtilityInstance().doStartActivityWithoutFinish(_activity, SearchHealthProviderActivity.class, "right");
            }
        });

        view.findViewById(R.id.llHealth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveFragment moveFragment = (MoveFragment) getActivity();
                moveFragment.moveFragment(new HealthMartDashBoardFragment());

            }
        });

        view.findViewById(R.id.llApps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWhichAppDownload();
            }
        });


    }// end setBodyUI()--------------


    /**
     * User can select from gallery and take image
     */
    @SuppressLint("NewApi")
    public void popupWhichAppDownload() {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_add_more_image);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(true);

        dialogMapMain.getWindow().setDimAmount(0.50f);

        dialogMapMain.show();

        TextView txtHealth = (TextView) dialogMapMain.findViewById(R.id.txtCamera);
        txtHealth.setText("DOWNLOAD DROOHEALTH");
        TextView txtFit = (TextView) dialogMapMain.findViewById(R.id.txtGallery);
        txtFit.setText("DOWNLOAD DROOFIT");

//DROOHEALTH
        dialogMapMain.findViewById(R.id.llcamera).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "com.drooHealth.main")));
                        dialogMapMain.dismiss();
                    }
                });
//DROOFIT
        dialogMapMain.findViewById(R.id.llGallery).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "com.fitness.drooFit.main")));
                        dialogMapMain.dismiss();
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

}

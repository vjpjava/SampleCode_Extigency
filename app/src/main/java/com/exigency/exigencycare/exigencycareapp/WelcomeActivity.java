package com.exigency.exigencycare.exigencycareapp;

/**
 * Welcome screen where user search doctor and use other apps
 *
 * @author GirnarSoft
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.fragment_slider.ContactUsFragment;
import com.exigency.exigencycare.fragment_slider.MyAppointmentFragment;
import com.exigency.exigencycare.fragment_slider.ProductFragment;
import com.exigency.exigencycare.fragment_slider.SearchFragment;
import com.exigency.exigencycare.interfaces.MoveFragment;
import com.exigency.exigencycare.interfaces.MoveFragmentWithOutAddbackStack;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;
import com.rey.material.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WelcomeActivity extends AppCompatActivity implements
        FragmentDrawer.FragmentDrawerListener, MoveFragment, MoveFragmentWithOutAddbackStack {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Activity _activity = this;
    private TextView txtHeader, txtEdit;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtEdit = (TextView) mToolbar
                .findViewById(R.id.txtRight);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // on Starting display homeFragment

        popupForCity();

        // displayView(0);

    }


    /**
     * Forgot password
     */
    @SuppressLint("NewApi")
    public void popupForCity() {
        final Dialog dialogMapMain = new Dialog(_activity);
        dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMapMain.setContentView(R.layout.popup_city);
        dialogMapMain.getWindow().setGravity(Gravity.CENTER);
        dialogMapMain.setCancelable(false);
        dialogMapMain.getWindow().setDimAmount(0.50f);
        dialogMapMain.show();


        spinner = (Spinner) dialogMapMain.findViewById(R.id.spCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(_activity, R.layout.custom_text, SplashActivity.arrCity);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utility.SetPreferences(_activity, "CITYID", SplashActivity.arrCityId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialogMapMain.findViewById(R.id.llDone).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        displayView(0);
                        dialogMapMain.dismiss();
                    }
                });


    }// end popup-----------------------


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



    // click event on right menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Filter:
                Utility.doStartActivityWithoutFinish(_activity, DepartmentActivity.class, "right");
                break;
            case R.id.action_city:
                //if (Utility.getPreferencesInteger(_activity, "USERID") == 0) {
                    //Utility.showMessage("You must be logged in to manage your Order.", _activity);
                popupForCity();
                /*} else {
                    Utility.doStartActivityWithoutFinish(_activity, TrackOrderListActivity.class, "right");
                }*/

                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected()---------------







    /**
     * used for switch fragment when click on slider items
     *
     * @author GirnarSoft
     */
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:

                fragment = new SearchFragment();
                title = getString(R.string.welcome);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 1:
                if (Utility.getPreferencesInteger(_activity, "USERID") == 0) {
                    showMessage("Please login or register for access My appointments!");
                } else {
                    fragment = new MyAppointmentFragment();
                    title = "My Appointment";
                    txtHeader.setText(title);
                    txtEdit.setVisibility(View.GONE);

                }


                break;


            case 2:
               /* fragment = new ReferAFriendFragment();
                title = getString(R.string.nav_item_refer_frd);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);*/

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "abc");
                intent.putExtra(Intent.EXTRA_TEXT, "def");
                intent.putExtra(Intent.EXTRA_CC, "ghi");
                intent.setType("text/html");
                startActivity(Intent.createChooser(intent, "Send mail"));

                break;
            case 3:
                fragment = new ContactUsFragment();
                title = getString(R.string.nav_item_contact_us);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 4:
                if (Utility.getPreferencesInteger(_activity, "USERID") == 0) {
                    showMessage("Login");
                } else {
                    popupLogout();
                }
                break;
            case 7:
                title = "Profile";
                break;
            default:
                break;
        }

        if (title.equalsIgnoreCase("PROFILE")) {
            Utility.doStartActivityWithoutFinish(_activity, ProfileActivity.class, "right");
        } else {
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(title);
            }
        }


    }// end displayView()---------------

    private void popupLogout() {
        new AlertDialog.Builder(_activity)
                .setIcon(R.drawable.icon)
                .setTitle("Logout")
                .setMessage("Are you sure you want to LOGOUT Account?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Utility.SetPreferencesInteger(_activity, "USERID", 0);

                                startActivity(new Intent(_activity, LoginRegistrationActivity.class));
                                _activity.finish();
                                overridePendingTransition(0, 0);
                            }

                        }).setNegativeButton("No", null).show();

    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            Utility.getUtilityInstance().doAnim(_activity, "left");
        } else {
            super.onBackPressed();
            Utility.getUtilityInstance().doAnim(_activity, "left");
        }
    }


    public void showMessage(String msg) {
        Toast.makeText(_activity, "" + msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void moveFragment(Fragment selectedFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, selectedFragment);
        transaction.addToBackStack(selectedFragment.getClass().getName());
        transaction.commit();

    }

    @Override
    public void moveFragmentWithoutAddbackStack(Fragment selectedFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, selectedFragment);
        transaction.commit();

    }


}// end main class--------------------
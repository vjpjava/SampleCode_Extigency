package com.android.droozo.droozoapp;

/**
 * Welcome screen where user search doctor and use other apps
 *
 * @author GirnarSoft
 */

import com.android.droozo.fragment_slider.ContactUsFragment;
import com.android.droozo.fragment_slider.MyAppointmentFragment;
import com.android.droozo.fragment_slider.MyTransactionFragment;
import com.android.droozo.fragment_slider.ReferAFriendFragment;
import com.android.droozo.fragment_slider.SettingsFragment;
import com.android.droozo.fragment_slider.WelcomeFragment;
import com.android.droozo.interfaces.MoveFragment;
import com.android.droozo.util.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements
        FragmentDrawer.FragmentDrawerListener, MoveFragment {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Activity _activity = this;
    private TextView txtHeader, txtEdit;

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
        displayView(0);

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }

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

                fragment = new WelcomeFragment();
                title = getString(R.string.welcome);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 1:

                if (Utility.getPreferencesInteger(_activity, "USERID") == 0) {
                    showMessage("Please login for access!");
                } else {
                    fragment = new MyAppointmentFragment();
                    title = "My Appointment";
                    txtHeader.setText(title);
                    txtEdit.setVisibility(View.GONE);
                }


                break;
            case 2:
                fragment = new MyTransactionFragment();
                title = getString(R.string.nav_item_myTransaction);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 3:
                fragment = new ReferAFriendFragment();
                title = getString(R.string.nav_item_refer_frd);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 4:
                fragment = new ContactUsFragment();
                title = getString(R.string.nav_item_contact_us);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 5:
                fragment = new SettingsFragment();
                title = getString(R.string.nav_item_settings);
                txtHeader.setText(title);
                txtEdit.setVisibility(View.GONE);
                break;
            case 6:
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

                                startActivity(new Intent(_activity, IntroSlideActivity.class));
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


}// end main class--------------------
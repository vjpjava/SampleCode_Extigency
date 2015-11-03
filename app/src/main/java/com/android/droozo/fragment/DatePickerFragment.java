package com.android.droozo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.droozo.droozoapp.DetailsActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by GSS-Vishnu Kant on 30/9/15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //String selectedDate = day + " " + new DateFormatSymbols().getMonths()[month] + " " + year;
        String selectedDate = day + "-" + month + "-" + year;

        // check which date and time option selected
        // 0 mean first date and time selected otherwise second date Time
        if (DetailsActivity.sIntWhichOne == 0) {
            DetailsActivity.sStrForFirstDate= selectedDate;
        } else {
            DetailsActivity.sStrForSecondDate = selectedDate;
        }

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
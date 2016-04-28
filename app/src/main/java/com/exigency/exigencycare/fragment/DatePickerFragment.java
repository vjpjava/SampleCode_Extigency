package com.exigency.exigencycare.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.exigency.exigencycare.exigencycareapp.DetailsActivity;
import com.exigency.exigencycare.interfaces.UpdateDateTimeInterface;

import java.util.Calendar;

/**
 * Created by  on 30/9/15.
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
        UpdateDateTimeInterface refUpdateDateTime = (UpdateDateTimeInterface) getActivity();
        //String selectedDate = day + " " + new DateFormatSymbols().getMonths()[month] + " " + year;
        String selectedDate = day + "-" + (month+1) + "-" + year;
        String reqselectedDate = year + "-" + (month+1) + "-" + day;

        // check which date and time option selected
        // 0 mean first date and time selected otherwise second date Time
        if (DetailsActivity.sIntWhichOne == 0) {
            DetailsActivity.sStrForFirstDate = selectedDate;
        } else {
            DetailsActivity.sStrForSecondDate = selectedDate;
        }
        refUpdateDateTime.updateDateTime(selectedDate, reqselectedDate);
        /*DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");*/
    }
}
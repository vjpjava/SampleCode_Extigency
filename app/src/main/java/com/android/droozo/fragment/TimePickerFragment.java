package com.android.droozo.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.droozo.droozoapp.DetailsActivity;
import com.android.droozo.interfaces.UpdateDateTimeInterface;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by GSS-Vishnu Kant on 30/9/15.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }//end onCreateDialog()------------------

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        UpdateDateTimeInterface refUpdateDateTime = (UpdateDateTimeInterface) getActivity();

        if (minute == 0) {
            minute = 00;
        }

        String selectedTime = hourOfDay + ":" + minute;
        // check which date and time option selected
        // 0 mean first date and time selected otherwise second date Time
        if (DetailsActivity.sIntWhichOne == 0) {
            DetailsActivity.sStrForFirstTime = selectedTime;
        } else {
            DetailsActivity.sStrForSecondTime = selectedTime;
        }
        refUpdateDateTime.updateDateTime(selectedTime);
    }// end onTimeSet()-------------------

}// end main class------------------
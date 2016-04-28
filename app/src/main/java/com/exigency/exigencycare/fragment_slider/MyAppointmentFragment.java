package com.exigency.exigencycare.fragment_slider;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.adapter.AppointmentListAdapter;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyAppointmentFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mlvAppointment;

    public MyAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myappointment_fragment, container,
                false);

        setBodyUI(rootView);
        //bookingDataListProcess();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void setBodyUI(View root) {
        mlvAppointment = (ListView) root.findViewById(R.id.lvAppointment);
        //
        mlvAppointment.setOnItemClickListener(this);

        if (Utility.getUtilityInstance().getConnectivityStatus(getActivity())) {
            Utility.runProgressDialog(getActivity());
            bookingDataListProcess();
        } else {
            Utility.showMessage("Please check your internet connection!!", getActivity());
        }


    }


    /**
     * get 4 category data like doctors
     */
    public void bookingDataListProcess() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("user_id", String.valueOf(Utility.getPreferencesInteger(getActivity(), "USERID")));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.usersappointments(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
                showMessage("failure " + arg0.toString());
                System.out.println("failure " + arg0.toString());
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                Utility.stopProgressDialog();

                if (result.getDoctors().size() == 0) {
                    Utility.showMessage("Oop's, No record found!!", getActivity());
                } else {
                    mlvAppointment.setAdapter(new AppointmentListAdapter(getActivity(), result.getDoctors()));
                }
            }
        });
    }// end HealthProviderProcess()-----------------


    public void showMessage(String msg) {
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*if (data.getAppointment_list().get(position).getUpdate_count() >= 2) {
            Utility.showMessage(" You already modified the timings twice. Now, you are not allow to modify.", getActivity());
        } else {
            Utility.doStartActivityWithoutFinishString3Value(getActivity(), BookDoctorDetailsActivity.class, "BOOKED_FOR", String.valueOf(data.getAppointment_list().get(position).getBooked_for()), "DATE1", String.valueOf(data.getAppointment_list().get(position).getDate_option1()), "DATE2", String.valueOf(data.getAppointment_list().get(position).getDate_option2()), "right");
        }*/
    }
}
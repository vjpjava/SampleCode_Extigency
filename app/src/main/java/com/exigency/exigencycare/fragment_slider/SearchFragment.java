package com.exigency.exigencycare.fragment_slider;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.adapter.SearchHospitalAdapter;
import com.exigency.exigencycare.adapter.SearchLabAdapter;
import com.exigency.exigencycare.adapter.SearchSpecialistAdapter;
import com.exigency.exigencycare.exigencycareapp.DepartmentActivity;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.SelectedLinearLayout;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchFragment extends Fragment {

    private ListView lvHome;
    private SelectedLinearLayout mSelectedLinearLayout;
    private LinearLayout llDoctor, llLabs, llHospital;
    ExigencyModel data;
    Toolbar mToolbar;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }// end onResume()-------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_health_provider, container,
                false);


        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.green_bg));

       /* TextView txtRight = (TextView) mToolbar.findViewById(R.id.txtRight);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("FILTER");

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(getActivity(), DepartmentActivity.class, "right");
            }
        });*/


        getActivity().findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.green_bg));

        initializeView(rootView);


        if (Utility.getUtilityInstance().getConnectivityStatus(getActivity())) {
            Utility.runProgressDialog(getActivity());
            getHomeData();
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }// end onCreateView()-------------------


    /**
     * Find widgets
     *
     * @param view
     */

    private void initializeView(View view) {

        mSelectedLinearLayout = (SelectedLinearLayout) view.findViewById(R.id.llHeader);
        mSelectedLinearLayout.setselected(0);

        llDoctor = (LinearLayout) view.findViewById(R.id.llDoctor);
        llLabs = (LinearLayout) view.findViewById(R.id.llLabs);
        llHospital = (LinearLayout) view.findViewById(R.id.llHospital);

        llDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedLinearLayout.setselected(0);
                if (data.getDoctors() == null || data.getDoctors().size() == 0) {
                    Toast.makeText(getActivity(), "Oop's No record found!", Toast.LENGTH_SHORT).show();
                } else {
                    lvHome.setAdapter(new SearchSpecialistAdapter(getActivity(), data.getDoctors()));
                }
            }
        });

        llLabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedLinearLayout.setselected(1);
                if (data.getLabs() == null || data.getLabs().size() == 0) {
                    Toast.makeText(getActivity(), "Oop's No record found!", Toast.LENGTH_SHORT).show();
                } else {
                    lvHome.setAdapter(new SearchLabAdapter(getActivity(), data.getLabs()));
                }
            }
        });

        llHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedLinearLayout.setselected(2);
                if (data.getHospitals() == null || data.getHospitals().size() == 0) {
                    Toast.makeText(getActivity(), "Oop's No record found!", Toast.LENGTH_SHORT).show();
                } else {
                    lvHome.setAdapter(new SearchHospitalAdapter(getActivity(), data.getHospitals()));
                }
            }
        });

        lvHome = (ListView) view.findViewById(R.id.lvHome);


    }// end initializeView()----------------


    /**
     * send forgot password request on server
     */
    private void getHomeData() {

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("city_id", "" + Utility.getPreferences(getActivity(), "CITYID"));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.getHomeDatas(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                data = result;

                if (result.getDoctors() == null || result.getDoctors().size() == 0) {
                    Toast.makeText(getActivity(), "Oop's No record found!", Toast.LENGTH_SHORT).show();
                } else {
                    lvHome.setAdapter(new SearchSpecialistAdapter(getActivity(), result.getDoctors()));
                }

                Utility.stopProgressDialog();
            }
        });
    }// end forgotPasswordService()---------------


    private String getStringFromEditText(EditText ed) {
        return ed.getText().toString().trim();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
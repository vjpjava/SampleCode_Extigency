package com.exigency.exigencycare.fragment_slider;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.ResponseData;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.interfaces.MoveFragmentWithOutAddbackStack;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContactUsFragment extends Fragment {

    private Toolbar toolbar;
    private TextView txtDone;
    private EditText edName, edEmail, edPhone, edComment;

    public ContactUsFragment() {
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
        //txtDone.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.facebook);

        /*txtDone = (TextView) toolbar.findViewById(R.id.txtRight);
        txtDone.setText("SEND");
        txtDone.setVisibility(View.VISIBLE);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for chech validation and send request to server
                checkValidation();
            }
        });*/
    }// end onResume()-------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_us_fragment, container,
                false);

        initializeView(rootView);

        return rootView;
    }// end onCreateView()-------------------


    /**
     * Find widgets
     *
     * @param view
     */

    private void initializeView(View view) {
        edName = (EditText) view.findViewById(R.id.edName);
        edEmail = (EditText) view.findViewById(R.id.edEmail);
        edPhone = (EditText) view.findViewById(R.id.edPhone);
        edComment = (EditText) view.findViewById(R.id.edDes);

    }// end initializeView()----------------


    private String getStringFromEditText(EditText ed) {
        return ed.getText().toString().trim();
    }


    /**
     * check validation and send request on server
     */
    private void checkValidation() {

        if (getStringFromEditText(edName).length() <= 0) {
            edName.setError("Please enter a Name!");
        } else if (getStringFromEditText(edEmail).length() <= 0) {
            edEmail.setError("Please enter a valid email address!");
        } else if (!Utility.isValidEmail(getStringFromEditText(edEmail))) {
            edEmail.setError("Please enter a valid email address!");
        } else if (getStringFromEditText(edPhone).length() <= 0) {
            edPhone.setError("Please enter a valid phone number!");
        } else if (getStringFromEditText(edComment).length() <= 0) {
            edComment.setError("Please enter your comments!");
        } else {
            sendRequestContactUs();
        }


    }//end checkValidation()--------------

    /**
     * send request on server for contact us
     */
    private void sendRequestContactUs() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("service_uuidgen", CommonMethods.Signature);
        request.put("contact_category", "android");
        request.put("contact_name", getStringFromEditText(edName));
        request.put("contact_email", getStringFromEditText(edEmail));
        request.put("contact_phone", getStringFromEditText(edPhone));
        request.put("contact_message", getStringFromEditText(edComment));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Response is--log---", msg);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);


        git.sendContactUS(request, new Callback<HealthProviderModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.showMessage("failure " + arg0.toString(), getActivity());
            }

            @Override
            public void success(HealthProviderModel result, Response arg2) {
                ResponseData data;
                data = result.getResponseData();

                // check status code if 500 then show message otherwise continue
                if (result.getStatusCode() == 500) {
                    Utility.showMessage("" + data.getMessage(), getActivity());
                } else {
                    Utility.showMessage("" + data.getMessage(), getActivity());
                }
            }
        });
    }// end sendRequestContactUs()-------------


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
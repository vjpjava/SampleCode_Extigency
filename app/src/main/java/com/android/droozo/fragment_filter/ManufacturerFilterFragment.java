package com.android.droozo.fragment_filter;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.droozo.adapter.ManufacturerFilterAdapter;
import com.android.droozo.droozoapp.R;

public class ManufacturerFilterFragment extends Fragment {

    private ListView mListView;
    private ManufacturerFilterAdapter mAdapter;
    private String[] strArray = new String[]{"Testing1", "Testing2", "Testing3", "Testing4", "Testing5"};


    public ManufacturerFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manu_filter_fragment, container,
                false);
        setBodyUI(rootView);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setBodyUI(View vi) {
        mListView = (ListView) vi.findViewById(R.id.lvManuFilter);
        mAdapter = new ManufacturerFilterAdapter(getActivity(), strArray);
        mListView.setAdapter(mAdapter);
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
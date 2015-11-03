package com.android.droozo.fragment_mart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.droozo.adapter.GridMartAdapter;
import com.android.droozo.droozoapp.R;

/**
 * Show user profile
 * Created by GSS-Vishnu Kant on 29/9/15.
 */
public class MartInnerFragment8 extends Fragment {
    private View view;
    private Activity activity;
    private GridView gridView;
    private static int pos;

    public static MartInnerFragment8 newInstance(int i) {
        MartInnerFragment8 profileFragment = new MartInnerFragment8();
        pos = i;
        return profileFragment;
    }


    public MartInnerFragment8() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mart_layout_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(new GridMartAdapter(getActivity(), pos));

        return view;
    }
}

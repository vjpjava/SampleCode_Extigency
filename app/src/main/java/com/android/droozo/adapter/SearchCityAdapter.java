package com.android.droozo.adapter;

/**
 * Created by GSS-Vishnu Kant on 29/9/15.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.droozo.Model.Doclist;
import com.android.droozo.droozoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchCityAdapter extends BaseAdapter {
    private Context mContext;
    private String[] arrCity;

    public SearchCityAdapter(Context c, String[] arr) {
        mContext = c;
        this.arrCity = arr;
    }

    class ViewHolder {
        private TextView txt;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrCity.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_single_text, null);

            holder.txt = (TextView) convertView.findViewById(R.id.txt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txt.setText(arrCity[position]);
        return convertView;
    }


}
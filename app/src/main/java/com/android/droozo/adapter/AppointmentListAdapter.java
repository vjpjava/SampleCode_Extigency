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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.droozo.Model.Appointment_list;
import com.android.droozo.Model.Doclist;
import com.android.droozo.droozoapp.R;
import com.android.droozo.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppointmentListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Appointment_list> arr;

    public AppointmentListAdapter(Context c, ArrayList<Appointment_list> arraay) {
        mContext = c;
        this.arr = arraay;
    }

    class ViewHolder {
        private LinearLayout llDelete;
        private TextView txtUsername, txtStatus, txtAddress;
        private TextView txtTiming1, txtTiming2, txtDate;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_appointment_item_list, null);

            holder.llDelete = (LinearLayout) convertView.findViewById(R.id.llDelete);
            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtTiming1 = (TextView) convertView.findViewById(R.id.txtTiming1);
            holder.txtTiming2 = (TextView) convertView.findViewById(R.id.txtTiming2);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtUsername.setText(arr.get(position).getDoc_name());
        holder.txtDate.setText(arr.get(position).getBooking_date());
        holder.txtAddress.setText(arr.get(position).getDoc_location());
        holder.txtTiming1.setText(arr.get(position).getDate_option1());
        holder.txtTiming2.setText(arr.get(position).getDate_option2());
        holder.txtStatus.setText("Status - " + arr.get(position).getBooking_status());


        return convertView;
    }


}
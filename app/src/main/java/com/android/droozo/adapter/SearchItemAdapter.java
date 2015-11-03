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

public class SearchItemAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Doclist> arrDocList;

    public SearchItemAdapter(Context c, ArrayList<Doclist> arr) {
        mContext = c;
        this.arrDocList = arr;
    }

    class ViewHolder {
        private ImageView imgUser, imgCall;
        private TextView txtUsername, txtQualification, txtSpeciality, txtAddress;
        private TextView txtTiming, txtPrice, txtRecommended;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrDocList.size();
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
            convertView = inflater.inflate(R.layout.row_search_item_list, null);

            holder.imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
            holder.imgCall = (ImageView) convertView.findViewById(R.id.imgCall);
            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.txtQualification = (TextView) convertView.findViewById(R.id.txtQualification);
            holder.txtSpeciality = (TextView) convertView.findViewById(R.id.txtSpeciality);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtTiming = (TextView) convertView.findViewById(R.id.txtTiming);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            holder.txtRecommended = (TextView) convertView.findViewById(R.id.txtRecommended);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (arrDocList.get(position).getPhoto().equalsIgnoreCase("")) {
            holder.imgUser.setBackgroundResource(R.drawable.default_thumb);
        } else {
            Picasso.with(mContext).load(arrDocList.get(position).getPhoto()).into(holder.imgUser);
        }
        holder.txtUsername.setText(arrDocList.get(position).getDoctor_name());

        if (arrDocList.get(position).getQualification().equalsIgnoreCase("")) {
            holder.txtQualification.setVisibility(View.GONE);
        } else {
            holder.txtQualification.setVisibility(View.VISIBLE);
            holder.txtQualification.setText(arrDocList.get(position).getQualification());
        }

        holder.txtSpeciality.setText(arrDocList.get(position).getSpeciality());
        holder.txtAddress.setText(arrDocList.get(position).getAddress());
        holder.txtPrice.setText(arrDocList.get(position).getFee_Charged());
        holder.txtTiming.setText(arrDocList.get(position).getDuty_timing());
        holder.txtRecommended.setText(arrDocList.get(position).getRecommended());

        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + arrDocList.get(position).getContact_num()));
                mContext.startActivity(callIntent);
            }
        });
        return convertView;
    }


}
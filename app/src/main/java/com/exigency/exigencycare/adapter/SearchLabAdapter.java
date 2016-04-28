package com.exigency.exigencycare.adapter;

/**
 * Created by  on 29/9/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exigency.exigencycare.Model.Doctors;
import com.exigency.exigencycare.Model.Labs;
import com.exigency.exigencycare.exigencycareapp.DetailsActivity;
import com.exigency.exigencycare.exigencycareapp.DetailsLabHospitalActivity;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.util.Utility;

import java.util.ArrayList;

public class SearchLabAdapter extends BaseAdapter {
    private Activity mContext;
    ArrayList<Labs> doctors;

    public SearchLabAdapter(Activity c, ArrayList<Labs> doc) {
        mContext = c;
        this.doctors = doc;
    }

    class ViewHolder {
        private ImageView imgUser, imgCall;
        private TextView txtUsername, txtQualification, txtSpeciality, txtAddress;
        private TextView txtPrice;
    }

    @Override
    public int getCount() {

        return doctors.size();

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_search_item_list, null);

            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.txtQualification = (TextView) convertView.findViewById(R.id.txtQualification);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(position);
        holder.txtUsername.setText(doctors.get(position).getName());
        holder.txtAddress.setText(doctors.get(position).getAddress());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsLabHospitalActivity.class);
                intent.putExtra("OBJECT", doctors.get((int) v.getTag()));
                mContext.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                mContext.startActivity(intent);
                Utility.doAnim(mContext, "right");

            }
        });
        return convertView;
    }
}
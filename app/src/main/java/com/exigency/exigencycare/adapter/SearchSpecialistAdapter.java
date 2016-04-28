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
import com.exigency.exigencycare.exigencycareapp.DetailsActivity;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.exigencycareapp.SearchListActivity;
import com.exigency.exigencycare.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchSpecialistAdapter extends BaseAdapter {
    private Activity mContext;
    ArrayList<Doctors> doctors;

    public SearchSpecialistAdapter(Activity c, ArrayList<Doctors> doc) {
        mContext = c;
        this.doctors = doc;
    }

    class ViewHolder {
        private ImageView imgUser, imgCall;
        private TextView txtUsername, txtQualification, txtSpeciality, txtAddress;
        private TextView txtPrice, txtSponsored;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

       // if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_search_item_list, null);

            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.txtQualification = (TextView) convertView.findViewById(R.id.txtQualification);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            holder.txtSponsored = (TextView) convertView.findViewById(R.id.txtSponsored);
            holder.imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
            convertView.setTag(holder);
       // } else {
       //     holder = (ViewHolder) convertView.getTag();
       // }


        convertView.setTag(position);

        try {
            Picasso.with(mContext).load("http://exigencycare.com/uploads/User%20Images/" + doctors.get(position).getUser_image()).placeholder(R.drawable.default_thumb).into(holder.imgUser);
        } catch (Exception e) {
            holder.imgUser.setBackgroundResource(R.drawable.default_thumb);
        }


        if (doctors.get(position).getSponsored().equalsIgnoreCase("1")) {
            holder.txtSponsored.setVisibility(View.VISIBLE);
        } else {
            holder.txtSponsored.setVisibility(View.INVISIBLE);
        }


        holder.txtUsername.setText(doctors.get(position).getName());
        holder.txtQualification.setText(doctors.get(position).getEducation());
        holder.txtAddress.setText(doctors.get(position).getAddress());
        holder.txtPrice.setText("₹" + doctors.get(position).getFee());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
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
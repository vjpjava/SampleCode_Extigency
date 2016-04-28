package com.exigency.exigencycare.adapter;

/**
 * Created by  on 29/9/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exigency.exigencycare.exigencycareapp.R;

public class SearchItemAdapter extends BaseAdapter {
    private Context mContext;

    public SearchItemAdapter(Context c) {
        mContext = c;
    }

    class ViewHolder {
        private ImageView imgUser, imgCall;
        private TextView txtUsername, txtQualification, txtSpeciality, txtAddress;
        private TextView txtTiming, txtPrice, txtRecommended;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 20;
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

            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.txtQualification = (TextView) convertView.findViewById(R.id.txtQualification);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


}
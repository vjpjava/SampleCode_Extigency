package com.android.droozo.adapter;

/**
 * Created by GSS-Vishnu Kant on 29/9/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SearchListActivity;
import com.android.droozo.util.Utility;
import com.squareup.picasso.Picasso;

public class CustomGridSearch extends BaseAdapter {
    private Context mContext;
    private String[] arrHealth;
    private String imgBaseUrl;

    public CustomGridSearch(Context c, String[] arr, String img) {
        mContext = c;
        this.arrHealth = arr;
        this.imgBaseUrl = img;
    }

    class ViewHolder {
        private ImageView imgGrid;
        private TextView txtGrid;
    }

    @Override
    public int getCount() {
        if (arrHealth != null) {
            return arrHealth.length;
        } else {
            return 0;
        }
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
            convertView = inflater.inflate(R.layout.row_grid_search, null);

            holder.txtGrid = (TextView) convertView.findViewById(R.id.txtGrid);
            holder.imgGrid = (ImageView) convertView.findViewById(R.id.imgGrid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtGrid.setText(arrHealth[position]);
        Picasso.with(mContext).load(imgBaseUrl + arrHealth[position] + ".png").placeholder(R.drawable.placeholder_health).into(holder.imgGrid);

        return convertView;
    }
}
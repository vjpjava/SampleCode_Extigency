package com.android.droozo.adapter;

/**
 * Created by GSS-Vishnu Kant on 29/9/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SingleItemMyCartActivity;
import com.android.droozo.util.Utility;

public class ManufacturerFilterAdapter extends BaseAdapter {
    private Context mContext;
    private String[] strArray;

    public ManufacturerFilterAdapter(Context c, String[] str) {
        mContext = c;
        this.strArray = str;
    }

    class ViewHolder {
        CheckBox txtName;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return strArray.length;
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
            convertView = inflater.inflate(R.layout.row_manu_filter, null);

            holder.txtName = (CheckBox) convertView.findViewById(R.id.check_contact);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(strArray[position]);


        return convertView;
    }
}
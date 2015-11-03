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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SingleItemMyCartActivity;
import com.android.droozo.util.Utility;

public class MyCartItemAdapter extends BaseAdapter {
    private Context mContext;

    public MyCartItemAdapter(Context c) {
        mContext = c;
    }

    class ViewHolder {
        private LinearLayout llitemDetails;
        private Spinner spinner;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2;
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
            convertView = inflater.inflate(R.layout.row_my_cart_item_list, null);
            holder.llitemDetails = (LinearLayout) convertView.findViewById(R.id.llitemDetails);
            holder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // click for see all details of clicked item from Your cart
        holder.llitemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(((Activity) mContext), SingleItemMyCartActivity.class, "right");
            }
        });

        String[] items = new String[20];
        for (int i = 0; i < items.length; i++)
            items[i] = String.valueOf(i + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, items);
        holder.spinner.setAdapter(adapter);


        return convertView;
    }
}
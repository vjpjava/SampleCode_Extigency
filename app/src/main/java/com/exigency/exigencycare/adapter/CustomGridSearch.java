package com.exigency.exigencycare.adapter;

/**
 * Created by  on 29/9/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exigency.exigencycare.Model.Departments;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.exigencycareapp.SearchListActivity;
import com.exigency.exigencycare.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridSearch extends BaseAdapter {
    private Context mContext;
    ArrayList<Departments> _data;

    public CustomGridSearch(Context c, ArrayList<Departments> arr) {
        mContext = c;
        this._data = arr;
    }

    class ViewHolder {
        private ImageView imgGrid;
        private TextView txtGrid;
    }

    @Override
    public int getCount() {
        if (_data != null) {
            return _data.size();
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

        //if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_grid_search, null);

            holder.txtGrid = (TextView) convertView.findViewById(R.id.txtGrid);
            holder.imgGrid = (ImageView) convertView.findViewById(R.id.imgGrid);
            convertView.setTag(holder);
        /*} else {
            holder = (ViewHolder) convertView.getTag();
        }*/

        convertView.setTag(position);


        holder.txtGrid.setText("" + _data.get(position).getDepartment_name());

        //holder.imgGrid.setBackgroundResource(R.drawable.placeholder_health);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinishStringValue((Activity) mContext, SearchListActivity.class, "DepID", _data.get((int) v.getTag()).getDepartment_id(), "right");
            }
        });


        return convertView;
    }
}
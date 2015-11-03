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

import com.android.droozo.droozoapp.HealthMartActivity;
import com.android.droozo.droozoapp.R;
import com.android.droozo.droozoapp.SingleItemActivity;
import com.android.droozo.util.Utility;

public class GridMartAdapter extends BaseAdapter {
    private Context mContext;
    private int positionOfList;

    public GridMartAdapter(Context c, int i) {
        mContext = c;
        positionOfList = i;
    }

    class ViewHolder {
        private ImageView imgGrid;
        private TextView txtName, txtRate;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 25;
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
            convertView = inflater.inflate(R.layout.row_grid_mart, null);

            //holder.txtGrid = (TextView) convertView.findViewById(R.id.txtGrid);
            holder.imgGrid = (ImageView) convertView.findViewById(R.id.imgProduct);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (positionOfList == 1) {
            holder.imgGrid.setBackgroundResource(R.drawable.thum_product);
        } else if (positionOfList == 2) {
            holder.imgGrid.setBackgroundResource(R.drawable.app);
        } else if (positionOfList == 3) {
            holder.imgGrid.setBackgroundResource(R.drawable.health_mart);
        } else {
            holder.imgGrid.setBackgroundResource(R.drawable.thum_product);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Your selected item is---" + positionOfList + " , " + position, Toast.LENGTH_SHORT).show();
                Utility.getUtilityInstance().doStartActivityWithoutFinish(((Activity) mContext), SingleItemActivity.class, "right");
            }
        });


        return convertView;
    }
}
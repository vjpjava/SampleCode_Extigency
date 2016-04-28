package com.exigency.exigencycare.adapter;

/**
 * Created by  on 29/9/15.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exigency.exigencycare.Api.ApiCaller;
import com.exigency.exigencycare.Model.Doctors;
import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.exigencycareapp.R;
import com.exigency.exigencycare.util.CommonMethods;
import com.exigency.exigencycare.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AppointmentListAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Doctors> arr;

    public AppointmentListAdapter(Context c, ArrayList<Doctors> ar) {
        mContext = c;
        this.arr = ar;
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
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtTiming1 = (TextView) convertView.findViewById(R.id.txtTiming1);
            holder.txtTiming2 = (TextView) convertView.findViewById(R.id.txtTiming2);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtUsername.setText(arr.get(position).getName());
        holder.txtAddress.setText(arr.get(position).getArea() + ", " + arr.get(position).getAddress());
        holder.txtTiming1.setText(arr.get(position).getBooking_date());
        holder.txtTiming2.setText(arr.get(position).getTime());


        if (arr.get(position).getAppointment_active().equalsIgnoreCase("0")) {
            holder.txtStatus.setText("Status - Pending");
        } else {
            holder.txtStatus.setText("Status - Confirmed");
        }


        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.getUtilityInstance().getConnectivityStatus((Activity) mContext)) {
                    Utility.runProgressDialog((Activity) mContext);
                    callServiceForDeleteAppointment(arr.get(position).getAppointment_id(), position);
                } else {
                    Utility.showMessage("Please check your internet connection!!", mContext);
                }


            }
        });


        return convertView;
    }


    /**
     * get All Catgories
     */
    private void callServiceForDeleteAppointment(String bookingID, final int pos) {
        final HashMap<String, String> request = new HashMap<String, String>();
        request.put("user_id", "" + Utility.getPreferencesInteger(mContext, "USERID"));
        request.put("appointment_id", bookingID);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CommonMethods.API).setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String msg) {
                        Log.i("Res DMART getCat-", msg);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL).build();

        ApiCaller git = restAdapter.create(ApiCaller.class);
        git.deletemyappointment(request, new Callback<ExigencyModel>() {
            @Override
            public void failure(RetrofitError arg0) {
                Utility.stopProgressDialog();
            }

            @Override
            public void success(ExigencyModel result, Response arg2) {
                Utility.stopProgressDialog();
                Utility.showMessage("Your appointment has been deleted successfully!!", mContext);
                arr.remove(pos);
                notifyDataSetChanged();
            }
        });
    }//end callServiceForGetCategories()---------java.net.SocketTimeoutException--------

}
package com.exigency.exigencycare.exigencycareapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.exigency.exigencycare.util.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String latitude;
    String longitude;

    private String LOCATION_NAME;

    // TODO: 26/10/15   Give Api info in Global calss file instead of here
    public static String API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    public static String API_KEY = "AIzaSyDkx-xZilKEYaE8Lmw5FB41YxqvA9ukhlI";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LOCATION_NAME = Utility.getStringExtraProcess("MAP", this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String addressUrl = API_BASE_URL +
                "address=" + LOCATION_NAME +
                "&" +
                "key=" + API_KEY;
        addressUrl = addressUrl.replaceAll(" ", "%20");
        new AsyncTaskRunner().execute(addressUrl);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String addressUrl = params[0];

                JSONObject addressInfoJsonObj = Utility.getLocationFromAddress(addressUrl);
                String status = addressInfoJsonObj.getString("status");
                if (status != null && status.equalsIgnoreCase("OK")) {
                    JSONArray resultsJsonArray = addressInfoJsonObj.getJSONArray("results");
                    if (resultsJsonArray != null) {
                        for (int i = 0; i < resultsJsonArray.length(); i++) {
                            JSONObject resultChildJsonObj = resultsJsonArray.getJSONObject(i);
                            JSONObject geometryJosnObj = resultChildJsonObj.getJSONObject("geometry");
                            JSONObject locationJsonObj = geometryJosnObj.getJSONObject("location");
                            latitude = locationJsonObj.getString("lat");
                            longitude = locationJsonObj.getString("lng");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return latitude + longitude;
        }

        @Override
        protected void onPostExecute(String result) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(latLng).title(LOCATION_NAME));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), 12.0f));
            } catch (Exception e) {
                Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_LONG).show();
            }
        }
    }

}

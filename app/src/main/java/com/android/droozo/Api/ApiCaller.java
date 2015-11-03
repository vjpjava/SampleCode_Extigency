package com.android.droozo.Api;

import com.android.droozo.Model.HealthProviderModel;
import com.android.droozo.Model.LoginRegisterModel;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface ApiCaller {
    @FormUrlEncoded
    @POST("/userRegister")
    public void getRegistrationData(@FieldMap HashMap<String, String> arr,
                                    retrofit.Callback<LoginRegisterModel> callback);

    @FormUrlEncoded
    @POST("/userLogin")
    public void getLoginData(@FieldMap HashMap<String, String> arr,
                             retrofit.Callback<LoginRegisterModel> callback);

    @FormUrlEncoded
    @POST("/userFacebookOauth")
    public void checkFBAuth(@FieldMap HashMap<String, String> arr,
                            retrofit.Callback<LoginRegisterModel> callback);

    @Multipart
    @POST("/uploadUserPicture")
    public void uploadImage(@Part("file") TypedFile file, @Part("userid") int userid, @Part("service_uuidgen") String service_uuidgen,
                            Callback<LoginRegisterModel> callback);

    @FormUrlEncoded
    @POST("/ProviderCategories")
    public void getHealthProvider(@FieldMap HashMap<String, String> arr,
                                  retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/DroozoSearch")
    public void getDoctorList(@FieldMap HashMap<String, String> arr,
                              retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/AutoCompleteCity")
    public void getCity(@FieldMap HashMap<String, String> arr,
                        retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/LocationByCity")
    public void getLocationByCity(@FieldMap HashMap<String, String> arr,
                                  retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/AutoCompleteDocNameCumSpeciality")
    public void getDoctorSpecialtiy(@FieldMap HashMap<String, String> arr,
                                    retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/createBooking")
    public void createBookingProcess(@FieldMap HashMap<String, String> arr,
                                     retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/BookingData")
    public void getBookingData(@FieldMap HashMap<String, String> arr,
                               retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/forgotPassword")
    public void forgotPassword(@FieldMap HashMap<String, String> arr,
                               retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/userProfile")
    public void getProfileDetails(@FieldMap HashMap<String, String> arr,
                                  retrofit.Callback<HealthProviderModel> callback);

}

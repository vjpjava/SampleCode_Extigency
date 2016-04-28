package com.exigency.exigencycare.Api;

import com.exigency.exigencycare.Model.ExigencyModel;
import com.exigency.exigencycare.Model.HealthProviderModel;
import com.exigency.exigencycare.Model.LoginRegisterModel;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface ApiCaller {
    @FormUrlEncoded
    @POST("/register")
    public void getRegistrationData(@FieldMap HashMap<String, String> arr,
                                    retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/authenticate")
    public void getLoginData(@FieldMap HashMap<String, String> arr,
                             retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/citywiselists")
    public void getHomeDatas(@FieldMap HashMap<String, String> arr,
                             retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/Citylist")
    public void getCityList(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/searchdoctor")
    public void searchdoctor(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);


    @FormUrlEncoded
    @POST("/usersappointments")
    public void usersappointments(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/doctortiminghome")
    public void doctortiminghome(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/deletemyappointment")
    public void deletemyappointment(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/bookappointment")
    public void bookappointment(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);

    @FormUrlEncoded
    @POST("/Deptlist")
    public void Deptlist(@FieldMap HashMap<String, String> arr, retrofit.Callback<ExigencyModel> callback);


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

    @FormUrlEncoded
    @POST("/updateProfile")
    public void updateProfile(@FieldMap HashMap<String, String> arr,
                              retrofit.Callback<HealthProviderModel> callback);

    @FormUrlEncoded
    @POST("/contactus")
    public void sendContactUS(@FieldMap HashMap<String, String> arr,
                              retrofit.Callback<HealthProviderModel> callback);

}

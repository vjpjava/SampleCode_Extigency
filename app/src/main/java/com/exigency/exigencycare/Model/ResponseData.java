package com.exigency.exigencycare.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sez1 on 14/10/15.
 */
public class ResponseData implements Serializable {
    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public String profile_image;
    public int userid;
    public String fb_database_id;
    public String user_type;



    public ArrayList<Cities> cities;

    public ArrayList<Cities> getCities() {
        return cities;
    }

    public void setCities(ArrayList<Cities> cities) {
        this.cities = cities;
    }

    public String message;

    public String[] doctor;
    public String[] diagnostic;
    public String[] ambulance;
    public String[] pharmacy;
    public String[] city_list;
    public String[] doc_name_cum_speciality;
    public String[] location_list;


    public String name;
    public String location;
    public String City;
    public String type;
    public int totalrecord;
    public ArrayList<Doclist> doclist;
    public ArrayList<Appointment_list> appointment_list;
    public Personal_detail personal_detail;

    public Personal_detail getPersonal_detail() {
        return personal_detail;
    }

    public void setPersonal_detail(Personal_detail personal_detail) {
        this.personal_detail = personal_detail;
    }

    public ArrayList<Appointment_list> getAppointment_list() {
        return appointment_list;
    }

    public void setAppointment_list(ArrayList<Appointment_list> appointment_list) {
        this.appointment_list = appointment_list;
    }

    public boolean success;
    public String ref;
    public String booked_for;
    public String booked_by;
    public String booking_type;
    public String date_option1;
    public String time_option1;
    public String date_option2;
    public String time_option2;
    public String self;
    public String other_name;
    public String other_mobile;
    public String other_email;
    public String booking_date;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getBooked_for() {
        return booked_for;
    }

    public void setBooked_for(String booked_for) {
        this.booked_for = booked_for;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(String booking_type) {
        this.booking_type = booking_type;
    }

    public String getDate_option1() {
        return date_option1;
    }

    public void setDate_option1(String date_option1) {
        this.date_option1 = date_option1;
    }

    public String getTime_option1() {
        return time_option1;
    }

    public void setTime_option1(String time_option1) {
        this.time_option1 = time_option1;
    }

    public String getDate_option2() {
        return date_option2;
    }

    public void setDate_option2(String date_option2) {
        this.date_option2 = date_option2;
    }

    public String getTime_option2() {
        return time_option2;
    }

    public void setTime_option2(String time_option2) {
        this.time_option2 = time_option2;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getOther_mobile() {
        return other_mobile;
    }

    public void setOther_mobile(String other_mobile) {
        this.other_mobile = other_mobile;
    }

    public String getOther_email() {
        return other_email;
    }

    public void setOther_email(String other_email) {
        this.other_email = other_email;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String[] getLocation_list() {
        return location_list;
    }

    public void setLocation_list(String[] location_list) {
        this.location_list = location_list;
    }

    public String[] getDoc_name_cum_speciality() {
        return doc_name_cum_speciality;
    }

    public void setDoc_name_cum_speciality(String[] doc_name_cum_speciality) {
        this.doc_name_cum_speciality = doc_name_cum_speciality;
    }

    public String[] getCity_list() {
        return city_list;
    }

    public void setCity_list(String[] city_list) {
        this.city_list = city_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalrecord() {
        return totalrecord;
    }

    public void setTotalrecord(int totalrecord) {
        this.totalrecord = totalrecord;
    }

    public ArrayList<Doclist> getDoclist() {
        return doclist;
    }

    public void setDoclist(ArrayList<Doclist> doclist) {
        this.doclist = doclist;
    }

    public String[] getDoctor() {
        return doctor;
    }

    public void setDoctor(String[] doctor) {
        this.doctor = doctor;
    }

    public String[] getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String[] diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String[] getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(String[] ambulance) {
        this.ambulance = ambulance;
    }

    public String[] getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String[] pharmacy) {
        this.pharmacy = pharmacy;
    }


    public String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFb_database_id() {
        return fb_database_id;
    }

    public void setFb_database_id(String fb_database_id) {
        this.fb_database_id = fb_database_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
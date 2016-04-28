package com.exigency.exigencycare.Model;

import java.io.Serializable;

/**
 * Created by sez1 on 20/10/15.
 */
public class Doclist implements Serializable {

    public int Id;


    public String C_id;
    public String Doctor_name;
    public String Qualification;
    public String Indivisual_speciality;
    public String Speciality;
    public String Year_experience;
    public String Fee_Charged;
    public String Address;
    public String Location;
    public String City;
    public String State;
    public String Pincode;

    public String getDuty_timing() {
        return duty_timing;
    }

    public void setDuty_timing(String duty_timing) {
        this.duty_timing = duty_timing;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String duty_timing;
    public String recommended;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public String getDoctor_name() {
        return Doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        Doctor_name = doctor_name;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getIndivisual_speciality() {
        return Indivisual_speciality;
    }

    public void setIndivisual_speciality(String indivisual_speciality) {
        Indivisual_speciality = indivisual_speciality;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getYear_experience() {
        return Year_experience;
    }

    public void setYear_experience(String year_experience) {
        Year_experience = year_experience;
    }

    public String getFee_Charged() {
        return Fee_Charged;
    }

    public void setFee_Charged(String fee_Charged) {
        Fee_Charged = fee_Charged;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getContact_num() {
        return Contact_num;
    }

    public void setContact_num(String contact_num) {
        Contact_num = contact_num;
    }

    public String getMobile_num() {
        return Mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        Mobile_num = mobile_num;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getStart_time1() {
        return Start_time1;
    }

    public void setStart_time1(String start_time1) {
        Start_time1 = start_time1;
    }

    public String getEnd_time1() {
        return End_time1;
    }

    public void setEnd_time1(String end_time1) {
        End_time1 = end_time1;
    }

    public String getStart_time2() {
        return Start_time2;
    }

    public void setStart_time2(String start_time2) {
        Start_time2 = start_time2;
    }

    public String getEnd_time2() {
        return End_time2;
    }

    public void setEnd_time2(String end_time2) {
        End_time2 = end_time2;
    }

    public String getClosed_day() {
        return Closed_day;
    }

    public void setClosed_day(String closed_day) {
        Closed_day = closed_day;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getClinic_name() {
        return Clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        Clinic_name = clinic_name;
    }

    public String getClphoto() {
        return clphoto;
    }

    public void setClphoto(String clphoto) {
        this.clphoto = clphoto;
    }

    public String Contact_num;
    public String Mobile_num;
    public String Email_id;
    public String Start_time1;
    public String End_time1;
    public String Start_time2;
    public String End_time2;
    public String Closed_day;
    public String photo;
    public String Token;
    public String modified_date;
    public String Clinic_name;
    public String clphoto;
}

package com.exigency.exigencycare.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sez1 on 14/10/15.
 */
public class ExigencyModel implements Serializable {

    private int success;
    private String token;
    private String result;

    User_details user_details;

    public User_details getUser_details() {
        return user_details;
    }

    public void setUser_details(User_details user_details) {
        this.user_details = user_details;
    }

    ArrayList<Departments> departments;

    public ArrayList<Departments> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Departments> departments) {
        this.departments = departments;
    }

    ArrayList<Timeslot> timeslot;

    public ArrayList<Timeslot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(ArrayList<Timeslot> timeslot) {
        this.timeslot = timeslot;
    }

    public ArrayList<SponsoredDoctors> getSponsoredDoctors() {
        return sponsoredDoctors;
    }

    public void setSponsoredDoctors(ArrayList<SponsoredDoctors> sponsoredDoctors) {
        this.sponsoredDoctors = sponsoredDoctors;
    }

    ArrayList<Doctors> doctors;
    ArrayList<SponsoredDoctors> sponsoredDoctors;

    ArrayList<Labs> labs;
    ArrayList<Hospitals> hospitals;

    public ArrayList<Labs> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<Labs> labs) {
        this.labs = labs;
    }

    public ArrayList<Hospitals> getHospitals() {
        return hospitals;
    }

    public void setHospitals(ArrayList<Hospitals> hospitals) {
        this.hospitals = hospitals;
    }

    public ArrayList<Doctors> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctors> doctors) {
        this.doctors = doctors;
    }

    ArrayList<Cities> cities;

    public ArrayList<Cities> getCities() {
        return cities;
    }

    public void setCities(ArrayList<Cities> cities) {
        this.cities = cities;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}


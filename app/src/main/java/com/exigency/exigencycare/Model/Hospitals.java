package com.exigency.exigencycare.Model;

import java.io.Serializable;

/**
 * Created by Vishnu Gupta on 1/11/2016.
 */
public class Hospitals implements Serializable{


    public String id;
    public String name;
    public String email;
    public String profile_tag_line;
    public String about;
    public String address;

    public String area;
    public String pincode;
    public String services;
    public String city_id;
    public String profile_small_description;

    public String getProfile_small_description() {
        return profile_small_description;
    }

    public void setProfile_small_description(String profile_small_description) {
        this.profile_small_description = profile_small_description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_tag_line() {
        return profile_tag_line;
    }

    public void setProfile_tag_line(String profile_tag_line) {
        this.profile_tag_line = profile_tag_line;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}

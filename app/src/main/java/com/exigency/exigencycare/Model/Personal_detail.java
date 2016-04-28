package com.exigency.exigencycare.Model;

/**
 * Created by sez1 on 27/10/15.
 */
public class Personal_detail {
    public int userid;
    public String fname;
    public String lname;
    public String phone;
    public String email;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getFb_database_id() {
        return fb_database_id;
    }

    public void setFb_database_id(String fb_database_id) {
        this.fb_database_id = fb_database_id;
    }

    public String profile_image;
    public String fb_database_id;
}

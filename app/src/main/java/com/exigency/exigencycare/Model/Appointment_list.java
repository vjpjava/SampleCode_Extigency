package com.exigency.exigencycare.Model;

/**
 * Created by sez1 on 27/10/15.
 */
public class Appointment_list {
    public int booking_id;
    public String booking_ref;
    public String doc_location;
    public String contact;
    public String doc_name;

    public String self_or_other;
    public String date_option1;
    public String date_option2;
    public String booking_date;
    public String booking_status;
    public int update_count;

    public int getBooked_for() {
        return booked_for;
    }

    public void setBooked_for(int booked_for) {
        this.booked_for = booked_for;
    }

    public int booked_for;


    public int getUpdate_count() {
        return update_count;
    }

    public void setUpdate_count(int update_count) {
        this.update_count = update_count;
    }

    public String confirmed_slot;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooking_ref() {
        return booking_ref;
    }

    public void setBooking_ref(String booking_ref) {
        this.booking_ref = booking_ref;
    }

    public String getDoc_location() {
        return doc_location;
    }

    public void setDoc_location(String doc_location) {
        this.doc_location = doc_location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getSelf_or_other() {
        return self_or_other;
    }

    public void setSelf_or_other(String self_or_other) {
        this.self_or_other = self_or_other;
    }

    public String getDate_option1() {
        return date_option1;
    }

    public void setDate_option1(String date_option1) {
        this.date_option1 = date_option1;
    }

    public String getDate_option2() {
        return date_option2;
    }

    public void setDate_option2(String date_option2) {
        this.date_option2 = date_option2;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getConfirmed_slot() {
        return confirmed_slot;
    }

    public void setConfirmed_slot(String confirmed_slot) {
        this.confirmed_slot = confirmed_slot;
    }
}

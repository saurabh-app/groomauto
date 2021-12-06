package com.example.hp.groomauto.fragament;

/**
 * Created by hp on 4/16/2018.
 */

public class Booking {

    String book_id,package_name,price,vehicle_company,date,time,status,vehicle_model, vehicle_number,pickup_drop,pickup_address,pickup_time;

    public String getBook_id() {
        return book_id;
    }

    public String getPickup_drop() {
        return pickup_drop;
    }

    public void setPickup_drop(String pickup_drop) {
        this.pickup_drop = pickup_drop;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public void setBook_id(String book_id) {

        this.book_id = book_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVehicle_company() {
        return vehicle_company;
    }

    public void setVehicle_company(String vehicle_company) {
        this.vehicle_company = vehicle_company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

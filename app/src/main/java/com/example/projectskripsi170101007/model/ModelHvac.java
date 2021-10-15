package com.example.projectskripsi170101007.model;

import com.google.firebase.database.PropertyName;

public class ModelHvac {

    private String code;
    private String name;
    private String brand;
    private String capacity;
    private String location;
    private String maintenanceDate;
    private String picture;
    private String user;
    private String modifiedDate;
    private String statusAset;
    private int position;
    private String key;

    public static ModelHvac get(int position) {
        return null;
    }

    @PropertyName("key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @PropertyName("code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @PropertyName("capacity")
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @PropertyName("location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @PropertyName("maintenanceDate")
    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    @PropertyName("modifiedDate")
    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @PropertyName("picture")
    public String getPicture() { return picture; }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @PropertyName("user")
    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }

    @PropertyName("statusAset")
    public String getStatusAset() {
        return statusAset;
    }

    public void setStatusAset(String statusAset) {
        this.statusAset = statusAset;
    }

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public ModelHvac(){
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public ModelHvac(String code, String name, String brand, String capacity, String location, String maintenanceDate , String modifiedDate, String picture, String user, String statusAset) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.capacity = capacity;
        this.location = location;
        this.maintenanceDate = maintenanceDate;
        this.modifiedDate = modifiedDate;
        this.picture = picture;
        this.user = user;
        this.statusAset = statusAset;

    }


}


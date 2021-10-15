package com.example.projectskripsi170101007.model;

import com.google.firebase.database.PropertyName;

public class ModelWoCivil {

    private String wocode;
    private String name;
    private String location;
    private String pic;
    private String remarks;
    private String status;
    private String picture;
    private String maintenanceDate;
    private String userInput;
    private String woDate;
    private String userStart;
    private String startDate;
    private String userFinish;
    private String finishDate;
    private String userApproved;
    private String approvedDate;
    private String userReject;
    private String rejectDate;
    private int position;
    private String key;

    public static ModelWoCivil get(int position) {
        return null;
    }

    @PropertyName("key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @PropertyName("wocode")
    public String getWocode() {
        return wocode;
    }

    public void setWocode(String wocode) {
        this.wocode = wocode;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @PropertyName("pic")
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) { this.pic = pic; }

    @PropertyName("maintenanceDate")
    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    @PropertyName("finishDate")
    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    @PropertyName("woDate")
    public String getWoDate() {
        return woDate;
    }

    public void setWoDate(String woDate) {
        this.woDate = woDate;
    }

    @PropertyName("remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("picture")
    public String getPicture() { return picture; }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @PropertyName("userFinish")
    public String getUserFinish() { return userFinish; }

    public void setUserFinish(String userFinish) {
        this.userFinish = userFinish;
    }

    @PropertyName("userInput")
    public String getUserInput() { return userInput; }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    @PropertyName("userStart")
    public String getUserStart() {
        return userStart;
    }

    public void setUserStart(String userStart) {
        this.userStart = userStart;
    }

    @PropertyName("startDate")
    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @PropertyName("userApproved")
    public String getUserApproved() { return userApproved; }

    public void setUserApproved(String userApproved) {
        this.userApproved = userApproved;
    }

    @PropertyName("approvedDate")
    public String getApprovedDate() { return approvedDate; }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    @PropertyName("userReject")
    public String getUserReject() { return userReject; }

    public void setUserReject(String userReject) {
        this.userReject = userReject;
    }

    @PropertyName("rejectDate")
    public String getRejectDate() { return rejectDate; }

    public void setRejectDate(String rejectDate) {
        this.rejectDate = rejectDate;
    }


    //Membuat Konstuktor kosong untuk membaca data snapshot
    public ModelWoCivil(){
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public ModelWoCivil( String wocode,
                              String name,
                              String location,
                              String pic,
                              String remarks,
                              String status,
                              String picture,
                              String maintenanceDate,
                              String userInput,
                              String woDate,
                              String userStart,
                              String startDate,
                              String userFinish,
                              String finishDate,
                              String userApproved,
                              String approvedDate,
                              String userReject,
                              String rejectDate) {
        this.finishDate = finishDate;
        this.location = location;
        this.maintenanceDate = maintenanceDate ;
        this.name = name;
        this.pic = pic;
        this.picture = picture;
        this.remarks = remarks;
        this.status = status;
        this.userFinish = userFinish;
        this.userInput =userInput;
        this.wocode = wocode;
        this.woDate = woDate;
        this.userStart = userStart;
        this.startDate = startDate;
        this.userApproved = userApproved;
        this.approvedDate = approvedDate;
        this.userReject = userReject;
        this.rejectDate = rejectDate;
    }
}



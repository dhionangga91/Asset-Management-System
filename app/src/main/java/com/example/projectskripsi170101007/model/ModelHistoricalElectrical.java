package com.example.projectskripsi170101007.model;

import com.google.firebase.database.PropertyName;

public class ModelHistoricalElectrical {

    private String pic;
    private String maintenanceDate;
    private String remarks;
    private int position;
    private String key;

    public static ModelHistoricalElectrical get(int position) {
        return null;
    }

    @PropertyName("key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @PropertyName("remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public ModelHistoricalElectrical(){
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public ModelHistoricalElectrical(String pic, String maintenanceDate , String remarks) {

        this.maintenanceDate = maintenanceDate ;
        this.pic = pic;
        this.remarks = remarks;

    }
}



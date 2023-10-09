package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentAffairDataModel implements Serializable
{

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("month_id")
    @Expose
    private String month_id;
    @SerializedName("display_name")
    @Expose
    private String display_name;

    public CurrentAffairDataModel(String month, String year, String month_id, String display_name) {
        this.month = month;
        this.year = year;
        this.month_id = month_id;
        this.display_name = display_name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth_id() {
        return month_id;
    }

    public void setMonth_id(String month_id) {
        this.month_id = month_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }


}



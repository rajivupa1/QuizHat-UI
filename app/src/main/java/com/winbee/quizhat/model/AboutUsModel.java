package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AboutUsModel implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    public String getUpdate_user_status() {
        return message;
    }

    public void setUpdate_user_status(String message) {
        this.message = message;
    }

}
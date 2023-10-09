package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentAffairDetailsMainModel implements Serializable {


    @SerializedName("success")
    @Expose
    private boolean Success;


    @SerializedName("message")
    @Expose
    private String Message;

    public CurrentAffairDetailsMainModel(boolean success, String message,CurrentAffairDetailsDataModel[] _data) {
        Success = success;
        Message = message;
        data = _data;
    }

    private CurrentAffairDetailsDataModel[] data;

    public boolean getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public CurrentAffairDetailsDataModel[] getData() {
        return data;
    }
}

package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SectionDetailsMainModel implements Serializable {
    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public SectionDetailsMainModel(boolean success, String message,SectionDetailsDataModel[] data) {
        Success = success;
        Message = message;
        Data = data;
    }

    private SectionDetailsDataModel[] Data;

    public boolean getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public SectionDetailsDataModel[] getData() {
        return Data;
    }
}

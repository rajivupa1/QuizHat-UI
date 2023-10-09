package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriptionMainModel implements Serializable {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public SubscriptionMainModel(boolean success, String message,SubscriptionDataModel[] data) {
        Success = success;
        Message = message;
        Data = data;
    }

    private SubscriptionDataModel[] Data;

    public boolean getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public SubscriptionDataModel[] getData() {
        return Data;
    }
}
package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SLDetailsMainModel {
    @Expose
    @SerializedName("Success")
    private String Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    private SLDetailsDataModel[] Data;

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public SLDetailsDataModel[] getData() {
        return Data;
    }
}

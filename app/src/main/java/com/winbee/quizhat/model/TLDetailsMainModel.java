package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TLDetailsMainModel {
    @Expose
    @SerializedName("Success")
    private String Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    private TLDetailsDataModel[] Data;

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public TLDetailsDataModel[] getData() {
        return Data;
    }
}

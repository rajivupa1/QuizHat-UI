package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OverviewMainModel  implements Serializable {

    @Expose
    @SerializedName("Success")
    private boolean Success;

    @Expose
    @SerializedName("Message")
    private String Message;

    public OverviewMainModel(boolean success, String message,OverviewDataModel[] data) {
        Success = success;
        Message = message;
        Data = data;
    }

    private OverviewDataModel[] Data;

    public boolean getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public OverviewDataModel[] getData() {
        return Data;
    }
}

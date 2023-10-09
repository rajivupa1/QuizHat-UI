package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OverviewDataModel implements Serializable {

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("ID")
    @Expose
    private String ID;

    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("BucketInfo")
    @Expose
    private String BucketInfo;
    @SerializedName("ItemAttachment")
    @Expose
    private String ItemAttachment;

    @SerializedName("Description")
    @Expose
    private String Description;

    public OverviewDataModel(String name, String ID, String status, String bucketInfo,
                             String itemAttachment, String description) {
        Name = name;
        this.ID = ID;
        Status = status;
        BucketInfo = bucketInfo;
        ItemAttachment = itemAttachment;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBucketInfo() {
        return BucketInfo;
    }

    public void setBucketInfo(String bucketInfo) {
        BucketInfo = bucketInfo;
    }

    public String getItemAttachment() {
        return ItemAttachment;
    }

    public void setItemAttachment(String itemAttachment) {
        ItemAttachment = itemAttachment;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

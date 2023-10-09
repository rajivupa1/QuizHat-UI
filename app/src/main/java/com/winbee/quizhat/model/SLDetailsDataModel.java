package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SLDetailsDataModel {
    @SerializedName("ExamName")
    @Expose
    private String ExamName;
    @SerializedName("ParentId")
    @Expose
    private String ParentId;
    @SerializedName("ChildId")
    @Expose
    private String ChildId;
    @SerializedName("Version")
    @Expose
    private String Version;
    @SerializedName("LogData")
    @Expose
    private String LogData;
    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("ItemAttachment")
    @Expose
    private String ItemAttachment;


    public SLDetailsDataModel(String examName, String parentId, String childId,
                              String version, String logData, String status, String itemAttachment) {
        ExamName = examName;
        ParentId = parentId;
        ChildId = childId;
        Version = version;
        LogData = logData;
        Status = status;
        ItemAttachment = itemAttachment;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getLogData() {
        return LogData;
    }

    public void setLogData(String logData) {
        LogData = logData;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getItemAttachment() {
        return ItemAttachment;
    }

    public void setItemAttachment(String itemAttachment) {
        ItemAttachment = itemAttachment;
    }
}
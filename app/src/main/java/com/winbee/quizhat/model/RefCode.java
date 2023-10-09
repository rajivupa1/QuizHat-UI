package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefCode implements Serializable {

    @SerializedName("login_status")
    @Expose
    private boolean login_status;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("role_encode")
    @Expose
    private String role_encode;
    @SerializedName("cred")
    @Expose
    private String cred;
    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("Coaching")
    @Expose
    private String Coaching;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("error_message")
    @Expose
    private String error_message;
    @SerializedName("Name")
    @Expose
    private String Name;



    public RefCode(String username, String userId, String password,String name) {
        this.username=username;
        UserId = userId;
        Password = password;
        Name=name;
    }

    public RefCode() {
    }

    public RefCode(boolean login_status) {
        this.login_status = login_status;
    }



    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_encode() {
        return role_encode;
    }

    public void setRole_encode(String role_encode) {
        this.role_encode = role_encode;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String cred) {
        this.cred = cred;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCoaching() {
        return Coaching;
    }

    public void setCoaching(String coaching) {
        Coaching = coaching;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
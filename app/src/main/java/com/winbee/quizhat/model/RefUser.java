package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefUser implements Serializable {

    @SerializedName("login_status")
    @Expose
    private boolean login_status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("error_message")
    @Expose
    private String error_message;


    public RefUser() {
    }

    public RefUser(String name, String mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }


    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
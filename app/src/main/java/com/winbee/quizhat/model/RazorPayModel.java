package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RazorPayModel implements Serializable {

@SerializedName("ApiKey")
@Expose
private String ApiKey;
@SerializedName("Secret_Key")
@Expose
private String secret_Key;

    public RazorPayModel(String apiKey, String secret_Key) {
        ApiKey = apiKey;
        this.secret_Key = secret_Key;
    }

    public String getAPI_Key() {
return ApiKey;
}

public void setAPI_Key(String aPI_Key) {
this.ApiKey = aPI_Key;
}

public String getSecret_Key() {
return secret_Key;
}

public void setSecret_Key(String secret_Key) {
this.secret_Key = secret_Key;
}

}
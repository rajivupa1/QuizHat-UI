package com.winbee.quizhat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriptionDataModel implements Serializable {

    @SerializedName("DisplayPrice")
    @Expose
    private int DisplayPrice;
    @SerializedName("DiscountPrice")
    @Expose
    private int DiscountPrice;
    @SerializedName("TotalDiscount")
    @Expose
    private int TotalDiscount;
    @SerializedName("Tenure")
    @Expose
    private String Tenure;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("SubscriptionId")
    @Expose
    private String SubscriptionId;

    @SerializedName("IsPaid")
    @Expose
    private int IsPaid;

    public SubscriptionDataModel(int displayPrice, int discountPrice, int totalDiscount,
                                 String tenure, String description, String subscriptionId,int isPaid) {
        DisplayPrice = displayPrice;
        DiscountPrice = discountPrice;
        TotalDiscount = totalDiscount;
        Tenure = tenure;
        Description = description;
        SubscriptionId = subscriptionId;
        IsPaid=isPaid;
    }

    public int getDisplayPrice() {
        return DisplayPrice;
    }

    public void setDisplayPrice(int displayPrice) {
        DisplayPrice = displayPrice;
    }

    public int getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        DiscountPrice = discountPrice;
    }

    public int getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public String getTenure() {
        return Tenure;
    }

    public void setTenure(String tenure) {
        Tenure = tenure;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubscriptionId() {
        return SubscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        SubscriptionId = subscriptionId;
    }

    public int getIsPaid() { return IsPaid; }

    public void setIsPaid(int isPaid) { IsPaid = isPaid; }
}

package org.clkr.mobile.api;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("pictureURL")
    public String pictureURL;

    @SerializedName("googleId")
    public String googleId;

}
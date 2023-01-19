package com.example.nusaht.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String username;


    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

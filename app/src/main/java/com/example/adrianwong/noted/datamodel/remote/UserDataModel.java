package com.example.adrianwong.noted.datamodel.remote;

import com.squareup.moshi.Json;

public class UserDataModel {

    @Json(name = "username")
    private String username;

    @Json(name = "password")
    private String password;

    @Json(name = "message")
    private String message;

    @Json(name = "access_token")
    private String accessToken;

    @Json(name = "refresh_token")
    private String refreshToken;

    public UserDataModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() { return message; }
}

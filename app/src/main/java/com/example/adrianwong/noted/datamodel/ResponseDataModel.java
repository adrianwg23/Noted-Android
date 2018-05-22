package com.example.adrianwong.noted.datamodel;

import com.squareup.moshi.Json;

import java.util.List;

public class ResponseDataModel {

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

    @Json(name = "user_id")
    private int userId;

    @Json(name = "notes")
    private List<NoteItem> serverNotes;

    public ResponseDataModel(String username, String password) {
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

    public int getUserId() { return userId; }

    public List<NoteItem> getServerNotes() {
        return serverNotes;
    }
}

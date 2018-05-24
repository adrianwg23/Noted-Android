package com.example.adrianwong.noted.util;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class Constants {

    SharedPreferences preferences;

    @Inject
    public Constants(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;

    public String getAccessToken() {
        return  preferences.getString("access_token", "failed");
    }

    public String getRefreshToken() {
        return preferences.getString("refresh_token", "failed");
    }

    public String getUsername() {
       return preferences.getString("username", "failed");
    }

    public int getUserId() {
        return preferences.getInt("user_id", 0);
    }
}

package com.example.adrianwong.noted.data.remote;

public class UrlManager {

    public static final String BASE_URL = "https://noted-api.herokuapp.com";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String LOGOUT_ACCESS = "/logout/access";
    public static final String LOGOUT_REFRESH = "/logout/refresh";
    public static final String REFRESH = "/refresh";
    public static final String NEW_NOTE = "/note/new";
    public static final String GET_NOTES = "/notes/{username}";
    public static final String UPDATE_NOTE = "/note/{id}";
    public static final String DELETE_NOTE = "/note/{id}";
    public static final String SYNC = "/note/{username}";

}

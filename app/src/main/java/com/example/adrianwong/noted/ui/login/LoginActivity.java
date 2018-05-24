package com.example.adrianwong.noted.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.adrianwong.noted.MyApplication;
import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.base.BaseActivity;
import com.example.adrianwong.noted.ui.list.ListActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    private static final String LOGIN_FRAG = "LOGIN_FRAG";

    @Inject
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getApp().getAppComponent().inject(this);


        // Check if user is already logged in
        if (preferences.getBoolean("is_logged_in", false)) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        FragmentManager manager = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) manager.findFragmentByTag(LOGIN_FRAG);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();
        }

        addFragmentToActivity(manager, fragment, R.id.root_activity_login, LOGIN_FRAG);
    }
}

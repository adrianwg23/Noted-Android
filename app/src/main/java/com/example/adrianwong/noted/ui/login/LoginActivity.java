package com.example.adrianwong.noted.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.base.BaseActivity;
import com.example.adrianwong.noted.ui.list.ListActivity;

public class LoginActivity extends BaseActivity {



    private static final String LOGIN_FRAG = "LOGIN_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (pref.getString("refresh_token", null) != null) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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

package com.example.adrianwong.noted.ui.list;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.adrianwong.noted.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("ListActivity", "test " +preferences.getString("refresh_token", null));
    }
}

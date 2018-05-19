package com.example.adrianwong.noted.ui.add;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.base.BaseActivity;

public class AddActivity extends BaseActivity {

    private static final String ADD_ACTIVITY = "ACTIVITY_ADD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager manager = getSupportFragmentManager();
        AddFragment fragment = (AddFragment) manager.findFragmentByTag(ADD_ACTIVITY);

        if (fragment == null) {
            fragment = AddFragment.newInstance();
        }

        addFragmentToActivity(manager, fragment, R.id.root_activity_add, ADD_ACTIVITY);
    }
}

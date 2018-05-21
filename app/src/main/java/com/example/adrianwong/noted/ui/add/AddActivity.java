package com.example.adrianwong.noted.ui.add;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        Intent intent = getIntent();

        if (fragment == null) {
            if (intent == null) {
                fragment = AddFragment.newInstance(AddFragment.DEFAULT_NOTE_ID);
            } else {
                int noteId = intent.getIntExtra(AddFragment.EXTRA_NOTE_ID, AddFragment.DEFAULT_NOTE_ID);
                fragment = AddFragment.newInstance(noteId);
            }
        }

        addFragmentToActivity(manager, fragment, R.id.root_activity_add, ADD_ACTIVITY);
    }
}

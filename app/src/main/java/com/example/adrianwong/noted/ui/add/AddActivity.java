package com.example.adrianwong.noted.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.base.BaseActivity;

public class AddActivity extends BaseActivity {

    private static final String ADD_ACTIVITY = "ACTIVITY_ADD";
    private AddFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (AddFragment) manager.findFragmentByTag(ADD_ACTIVITY);

        Intent intent = getIntent();

        if (fragment == null) {
            if (!intent.hasExtra(AddFragment.EXTRA_NOTE_ID)) {
                fragment = AddFragment.newInstance(AddFragment.DEFAULT_NOTE_ID);
            } else {
                int noteId = intent.getIntExtra(AddFragment.EXTRA_NOTE_ID, AddFragment.DEFAULT_NOTE_ID);
                fragment = AddFragment.newInstance(noteId);
            }
        }

        addFragmentToActivity(manager, fragment, R.id.root_activity_add, ADD_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        if (!fragment.hasNoteChanged()) {
            super.onBackPressed();
        } else {
            fragment.onBackPressed();
        }
    }
}

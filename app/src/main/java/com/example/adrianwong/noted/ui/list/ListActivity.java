package com.example.adrianwong.noted.ui.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.base.BaseActivity;

public class ListActivity extends BaseActivity {

    private static final String LIST_FRAG = "LIST_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager manager = getSupportFragmentManager();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(LIST_FRAG);

        if (fragment == null) {
            fragment = ListFragment.newInstance();
        }

        addFragmentToActivity(manager, fragment, R.id.root_activity_list, LIST_FRAG);
    }
}

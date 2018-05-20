package com.example.adrianwong.noted.ui.add;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.util.PresenterHelper;
import com.example.adrianwong.noted.util.Priority;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements AddContract.AddView {

    @BindView(R.id.et_note_title) EditText mNoteTitleEt;
    @BindView(R.id.et_note_body) EditText mNoteBodyEt;
    @BindView(R.id.spinner_priority) Spinner mPrioritySpinner;

    AddPresenter mAddPresenter;
    private int mPriority = Priority.GREEN; // Green

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        initViews();

        return rootView;
    }

    public static AddFragment newInstance() { return new AddFragment(); }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddPresenter = new AddPresenter(PresenterHelper.getDataSource());
        mAddPresenter.attachView(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_editor, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String noteTitle = mNoteTitleEt.getText().toString();
                String noteBody = mNoteBodyEt.getText().toString();
                Date date = new Date();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddPresenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAddPresenter.onStop();
    }

    @Override
    public void initViews() {
        ArrayAdapter prioritySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array_options, android.R.layout.simple_dropdown_item_1line);
        prioritySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mPrioritySpinner.setAdapter(prioritySpinnerAdapter);
        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.priority_green))) {
                        mPriority = Priority.GREEN;
                    } else if (selection.equals(getString(R.string.priority_yellow))) {
                        mPriority = Priority.YELLOW;
                    } else {
                        mPriority = Priority.RED;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPriority = Priority.GREEN;
            }
        });
    }
}

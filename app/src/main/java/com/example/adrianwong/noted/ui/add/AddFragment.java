package com.example.adrianwong.noted.ui.add;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.util.InjectorUtil;
import com.example.adrianwong.noted.util.PresenterHelper;
import com.example.adrianwong.noted.util.Priority;
import com.example.adrianwong.noted.viewmodel.AddViewModel;
import com.example.adrianwong.noted.viewmodel.AddViewModelFactory;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements AddContract.AddView {

    @BindView(R.id.et_note_title)
    EditText mNoteTitleEt;
    @BindView(R.id.et_note_body)
    EditText mNoteBodyEt;
    @BindView(R.id.spinner_priority)
    Spinner mPrioritySpinner;

    AddPresenter mAddPresenter;
    private int mPriority = Priority.GREEN; // Green
    private AddViewModel mViewModel;

    private SharedPreferences pref;

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_NOTE_ID = "extraTaskId";

    // Constant for default task id to be used when not in update mode
    public static final int DEFAULT_NOTE_ID = -1;

    private int mNoteId = DEFAULT_NOTE_ID;

    private boolean mNoteHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
             mNoteHasChanged = true;
            return false;
        }
    };

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
        pref = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());

        initViews();

        return rootView;
    }

    public static AddFragment newInstance(int noteId) {
        AddFragment fragment = new AddFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_NOTE_ID, noteId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddPresenter = new AddPresenter(PresenterHelper.getDataSource());
        mAddPresenter.attachView(this);

        Bundle args = getArguments();
        mNoteId = args.getInt(EXTRA_NOTE_ID);

        setupViewModel();

        Log.d("AddFragment", "noteid: " + mNoteId);
        if (mNoteId != DEFAULT_NOTE_ID) {
            mViewModel.getNote().observe(this, noteItem -> {
                populateUi(noteItem);
            });
        }
    }

    private void populateUi(NoteItem noteItem) {
        String noteTitle = noteItem.getNoteTitle();
        String noteBody = noteItem.getNoteBody();
        int priority = noteItem.getPriority();

        mNoteTitleEt.setText(noteTitle);
        mNoteBodyEt.setText(noteBody);
        setPrioritySpinner(priority);
    }

    private void setPrioritySpinner(int priority) {
        switch (priority) {
            case Priority.GREEN:
                mPrioritySpinner.setSelection(Priority.GREEN);
                break;
            case Priority.YELLOW:
                mPrioritySpinner.setSelection(Priority.YELLOW);
                break;
            case Priority.RED:
                mPrioritySpinner.setSelection(Priority.RED);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_editor, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String noteTitle = mNoteTitleEt.getText().toString();
        String noteBody = mNoteBodyEt.getText().toString();
        Date date = new Date();
        Long time = date.getTime();
        int userId = pref.getInt("user_id", 0);
        NoteItem note = new NoteItem(noteTitle, noteBody, time, mPriority, userId);

        String accessToken = pref.getString("access_token", "failed");

        switch (item.getItemId()) {
            case R.id.action_save:
                if (mNoteId == DEFAULT_NOTE_ID) {
                    mViewModel.insertRemoteNote(accessToken, note);
                    mViewModel.insertNote(note);
                } else {
                    note.setId(mNoteId);
                    mViewModel.updateRemoteNote(accessToken, note);
                    mViewModel.updateNote(note);
                }
                getActivity().finish();
                return true;
            case android.R.id.home:
                if (!mNoteHasChanged) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                    return true;
                }

                onBackPressed();
                return true;
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
        mViewModel.onStop();
    }

    @Override
    public void initViews() {
        mNoteTitleEt.setOnTouchListener(mTouchListener);
        mNoteBodyEt.setOnTouchListener(mTouchListener);
        mPrioritySpinner.setOnTouchListener(mTouchListener);

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

    @Override
    public void setupViewModel() {
        AddViewModelFactory factory = InjectorUtil.provideAddViewModelFactory(getContext().getApplicationContext(), mNoteId);
        mViewModel = ViewModelProviders.of(this, factory).get(AddViewModel.class);
    }

    @Override
    public void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, (dialog, which) -> {
            // User clicked the "Keep editing" button, so dismiss the dialog
            // and continue editing the pet.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean hasNoteChanged() {
        return mNoteHasChanged;
    }

}

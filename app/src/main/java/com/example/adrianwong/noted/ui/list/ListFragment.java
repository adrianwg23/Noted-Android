package com.example.adrianwong.noted.ui.list;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.adapter.NoteAdapter;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.ui.add.AddActivity;
import com.example.adrianwong.noted.ui.add.AddFragment;
import com.example.adrianwong.noted.ui.login.LoginActivity;
import com.example.adrianwong.noted.util.InjectorUtil;
import com.example.adrianwong.noted.util.PresenterHelper;
import com.example.adrianwong.noted.viewmodel.ListViewModel;
import com.example.adrianwong.noted.viewmodel.ListViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.ListView, View.OnClickListener, NoteAdapter.InteractionListener {

    @BindView(R.id.fab_add_note) FloatingActionButton mFabulous;
    @BindView(R.id.rec_note_list) RecyclerView mRecyclerView;
    @BindView(R.id.list_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    private ListPresenter mListPresenter;
    private NoteAdapter mNoteAdapter;
    private ListViewModel mViewModel;
    private SharedPreferences pref;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() { return new ListFragment(); }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                String accessToken = pref.getString("access_token", "failed");
                String refreshToken = pref.getString("refresh_token", "failed");
                mListPresenter.logout(accessToken, refreshToken, mNoteAdapter.getNoteList());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());

        mNoteAdapter = new NoteAdapter(getContext());
        mNoteAdapter.setListInteractionListener(this);

        mFabulous.setOnClickListener(this);

        initViews();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListPresenter = new ListPresenter(InjectorUtil.provideRepository(getContext().getApplicationContext()), pref.edit());
        mListPresenter.attachView(this);
        setupViewModel();

        if (!pref.getBoolean("list_fetched", false)) { makeRequest(); }
    }

    @Override
    public void onStop() {
        super.onStop();
        mListPresenter.onStop();
        mViewModel.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mListPresenter.detachView();
    }

    @Override
    public void startAddActivity() {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void insertToDb(List<NoteItem> notes) {
        mViewModel.insertMultipleNotes(notes);
    }

    @Override
    public void makeRequest() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("list_fetched", true).apply();
        String accessToken = pref.getString("access_token", "failed");
        String username = pref.getString("username", "failed");

        mListPresenter.fetchNotes(accessToken, username);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mNoteAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallBack());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void setupViewModel() {
        int user_id = pref.getInt("user_id", 0);
        ListViewModelFactory factory = InjectorUtil.provideListViewModelFactory(getContext().getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);
        mViewModel.getNoteList(user_id).observe(this, noteList -> {
            showLoading();
            mNoteAdapter.setNotes(noteList);
            hideLoading();
        });
    }

    @Override
    public void showUndoSnackBar() {
        Snackbar.make(
                mCoordinatorLayout,
                getString(R.string.action_delete_item),
                Snackbar.LENGTH_LONG
        )
                .setAction(R.string.action_undo, v -> {
                    String accessToken = pref.getString("access_token", "failed");
                    mViewModel.onUndoConfirmed();
                    mViewModel.onUndoConfirmedRemote(accessToken);
                })
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                            mViewModel.onSnackBarTimeout();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        mListPresenter.onAddFabClick();
    }

    @Override
    public void onListClick(int itemId) {
        Log.d("ListFragment", "itemid: " + itemId);
        Intent intent = new Intent(getActivity(), AddActivity.class);
        intent.putExtra(AddFragment.EXTRA_NOTE_ID, itemId);
        startActivity(intent);
    }

    private ItemTouchHelper.Callback createHelperCallBack() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<NoteItem> notes = mNoteAdapter.getNoteList();
                NoteItem note = notes.get(position);
                String accessToken = pref.getString("access_token", "failed");

                mViewModel.deleteRemoteNote(accessToken, note.getId());
                mViewModel.deleteNote(note);
                showUndoSnackBar();
            }
        };
        return simpleCallback;
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mFabulous.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mFabulous.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }
}

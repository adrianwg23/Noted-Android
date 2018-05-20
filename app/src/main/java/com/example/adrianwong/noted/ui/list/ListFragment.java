package com.example.adrianwong.noted.ui.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.adapter.NoteAdapter;
import com.example.adrianwong.noted.data.local.NoteItem;
import com.example.adrianwong.noted.ui.add.AddActivity;
import com.example.adrianwong.noted.util.InjectorUtil;
import com.example.adrianwong.noted.util.PresenterHelper;
import com.example.adrianwong.noted.viewmodel.ListViewModel;
import com.example.adrianwong.noted.viewmodel.ListViewModelFactory;

import java.util.ArrayList;
import java.util.Date;
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

    private ListPresenter mListPresenter;
    private NoteAdapter mNoteAdapter;
    private ListViewModel mViewModel;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() { return new ListFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        mNoteAdapter = new NoteAdapter(getContext());
        mNoteAdapter.setListInteractionListener(this);

        mFabulous.setOnClickListener(this);

        initViews();
        setupViewModel();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListPresenter = new ListPresenter(PresenterHelper.getDataSource());
        mListPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListPresenter.onStop();
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
    public void startDetailActivity() {
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
    }

    @Override
    public void setupViewModel() {
        ListViewModelFactory factory = InjectorUtil.provideListViewModelFactory(getContext().getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);
        mViewModel.getNoteList().observe(this, noteList -> {
            showLoading();
            mNoteAdapter.setNotes(noteList);
            hideLoading();
        });
    }

    @Override
    public void onClick(View v) {
        mListPresenter.onAddFabClick();
    }

    @Override
    public void onListClick(NoteItem note) {

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

package com.example.adrianwong.noted.ui.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.adapter.NoteAdapter;
import com.example.adrianwong.noted.data.local.NoteItem;
import com.example.adrianwong.noted.ui.add.AddActivity;
import com.example.adrianwong.noted.util.PresenterHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.ListView, View.OnClickListener, NoteAdapter.InteractionListener {

    @BindView(R.id.fab_add_note) FloatingActionButton mFabulous;

    private ListPresenter mListPresenter;
    private NoteAdapter mNoteAdapter;

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

        mFabulous.setOnClickListener(this);
        mNoteAdapter.setListInteractionListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListPresenter = new ListPresenter(PresenterHelper.getDataSource());
        mListPresenter.attachView(this);
        mNoteAdapter = new NoteAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void onClick(View v) {
        mListPresenter.onAddFabClick();
    }

    @Override
    public void onListClick(NoteItem note) {

    }
}

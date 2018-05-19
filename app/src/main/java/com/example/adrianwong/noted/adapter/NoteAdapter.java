package com.example.adrianwong.noted.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.data.local.NoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemViewHolder> {

    private List<NoteItem> mNoteList;
    private InteractionListener mListInteractionListener;

    public NoteAdapter() {
        mNoteList = new ArrayList<>();
        mListInteractionListener = null;
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        NoteItem note = mNoteList.get(position);
        holder.mNoteTitleTv.setText(note.getNoteTitle());
        holder.mNoteBodyTv.setText(note.getNoteBody());
        holder.mPriorityView.setBackgroundColor(note.getPriority());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NoteItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_note_title) TextView mNoteTitleTv;
        @BindView(R.id.tv_note_body) TextView mNoteBodyTv;
        @BindView(R.id.priority_view) View mPriorityView;
        @BindView(R.id.tv_date) TextView mDateTv;
        @BindView(R.id.tv_time) TextView mTimeTv;


        public NoteItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface InteractionListener {
        void onListClick(NoteItem note);
    }

    public void setListInteractionListener(InteractionListener interactionListener) {
        mListInteractionListener = interactionListener;
    }
}

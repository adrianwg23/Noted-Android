package com.example.adrianwong.noted.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.util.Priority;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private List<NoteItem> mNoteList;
    private Context mContext;
    private InteractionListener mListInteractionListener;

    public NoteAdapter(Context context) {
        mContext = context;
        mNoteList = new ArrayList<>();
        mListInteractionListener = null;
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        Log.d("NoteAdapter", "parent: " + parent);
        return new NoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        NoteItem note = mNoteList.get(position);
        Date date = new Date(note.getUpdatedAt());

        String noteTitle = note.getNoteTitle();
        String noteBody = note.getNoteBody();
        int priority = note.getPriority();
        String updatedAt = dateFormat.format(date);

        holder.mNoteTitleTv.setText(noteTitle);
        holder.mNoteBodyTv.setText(noteBody);
        holder.mPriorityView.setBackgroundColor(getPriorityColour(priority));
        holder.mDateTv.setText(updatedAt);
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public void setNotes(List<NoteItem> noteList) {
        mNoteList = noteList;
        notifyDataSetChanged();
    }

    public List<NoteItem> getNotes() {
        return mNoteList;
    }

    private int getPriorityColour(int priority) {
        int priorityColour = 0;

        switch (priority) {
            case Priority.GREEN:
                priorityColour = ContextCompat.getColor(mContext, R.color.GREEN);
                break;
            case Priority.YELLOW:
                priorityColour = ContextCompat.getColor(mContext, R.color.YELLOW);
                break;
            case Priority.RED:
                priorityColour = ContextCompat.getColor(mContext, R.color.RED);
                break;
            default:
                break;
        }

        return priorityColour;
    }

    public List<NoteItem> getNoteList() {
        return mNoteList;
    }

    public boolean isEmpty() {
        return mNoteList.isEmpty();
    }

    public interface InteractionListener {
        void onListClick(int noteId);
    }

    public void setListInteractionListener(InteractionListener interactionListener) {
        mListInteractionListener = interactionListener;
    }

    public class NoteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_note_title)
        TextView mNoteTitleTv;
        @BindView(R.id.tv_note_body)
        TextView mNoteBodyTv;
        @BindView(R.id.priority_view)
        View mPriorityView;
        @BindView(R.id.tv_date)
        TextView mDateTv;

        public NoteItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListInteractionListener != null) {
                int elementId = mNoteList.get(getAdapterPosition()).getId();
                Log.d("NoteAdapter", "elementid: " + elementId);
                mListInteractionListener.onListClick(elementId);
            }
        }
    }
}

package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.data.local.NoteItem;
import com.example.adrianwong.noted.data.remote.NotedRestAdapter;

import java.util.List;

public class ListViewModel extends ViewModel {

    private final NotesRepository mRepository;
    private final LiveData<List<NoteItem>> mNoteList;

    private NoteItem tempNoteItem;

    public ListViewModel(NotesRepository repository) {
        this.mRepository = repository;
        this.mNoteList = repository.getNotes();
    }

    public LiveData<List<NoteItem>> getNoteList() {
        return mNoteList;
    }

    public void deleteNote(NoteItem note) {
        mRepository.deleteNote(note);
        tempNoteItem = note;
    }

    public void onUndoConfirmed() {
        mRepository.insertNote(tempNoteItem);
        tempNoteItem = null;
    }

    public void onSnackBarTimeout() {
        tempNoteItem = null;
    }
}

package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.data.local.NoteItem;

public class AddViewModel extends ViewModel {

    private final NotesRepository mRepository;
    private int mNoteId;

    public AddViewModel(NotesRepository repository, int noteId) {
        mRepository = repository;
        mNoteId = noteId;
    }

    public LiveData<NoteItem> getNote() { return mRepository.getNote(mNoteId); }

    public void insertNote(NoteItem noteItem) { mRepository.insertNote(noteItem); }

    public void updateNote(NoteItem noteItem) { mRepository.updateNote(noteItem); }
}

package com.example.adrianwong.noted.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.adrianwong.noted.data.local.NoteDao;
import com.example.adrianwong.noted.data.local.NoteItem;
import com.example.adrianwong.noted.data.remote.NotedRestAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotesRepository {

    public static final Object LOCK = new Object();
    public static NotesRepository sInstance;
    private final NoteDao mNoteDao;
    private final NotedRestAdapter mRestAdapter;
    private static final String LOG_TAG = NotesRepository.class.getSimpleName();

    public NotesRepository(NoteDao noteDao, NotedRestAdapter restAdapter) {
        this.mNoteDao = noteDao;
        this.mRestAdapter = restAdapter;
    }

    public static synchronized NotesRepository getIntance(NoteDao noteDao, NotedRestAdapter restAdapter) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NotesRepository(noteDao, restAdapter);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<List<NoteItem>> getNotes() {
        return mNoteDao.loadAllTasks();
    }

    public LiveData<NoteItem> getNote(int noteId) {
        return mNoteDao.loadTaskById(noteId);
    }

    public void saveNote(final NoteItem noteItem) {
        Observable.fromCallable(() -> {
            mNoteDao.insertNote(noteItem);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateNote(NoteItem noteItem) {
        mNoteDao.updateNote(noteItem);
    }

    public void deleteNote(NoteItem noteItem) {
        mNoteDao.deleteNote(noteItem);
    }

}

package com.example.adrianwong.noted.data;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.adrianwong.noted.data.local.NoteDao;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.data.remote.NotedRestAdapter;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.schedulers.Schedulers;

public class NotesRepository {

    public static final Object LOCK = new Object();
    public static NotesRepository sInstance;
    private final NoteDao mNoteDao;
    private final NotedRestAdapter mRestAdapter;
    private static final String LOG_TAG = NotesRepository.class.getSimpleName();

    @Inject
    public NotesRepository(NoteDao noteDao, NotedRestAdapter restAdapter) {
        this.mNoteDao = noteDao;
        this.mRestAdapter = restAdapter;
    }

    public static synchronized NotesRepository getInstance(NoteDao noteDao, NotedRestAdapter restAdapter) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NotesRepository(noteDao, restAdapter);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<List<NoteItem>> getNotes(int id) {
        return mNoteDao.loadAllTasks(id);
    }

    public LiveData<NoteItem> getNote(int noteId) {
        return mNoteDao.loadTaskById(noteId);
    }

    public void insertNote(final NoteItem noteItem) {
        Observable.fromCallable(() -> {
            mNoteDao.insertNote(noteItem);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void insertMultipleNotes(final List<NoteItem> notes) {
        Observable.fromCallable(() -> {
            mNoteDao.insertMultipleNotes(notes);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateNote(NoteItem noteItem) {
        Observable.fromCallable(() -> {
            mNoteDao.updateNote(noteItem);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteNote(NoteItem noteItem) {
        Observable.fromCallable(() -> {
            mNoteDao.deleteNote(noteItem);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteAll(List<NoteItem> notes) {
        Observable.fromCallable(() -> {
            mNoteDao.deleteAll(notes);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Observable<ResponseDataModel> deleteRemoteNote(String accessToken, int noteId) {
        return mRestAdapter.deleteNotes(accessToken, noteId);
    }

    public Observable<ResponseDataModel> newRemoteNote(String accessToken, NoteItem note) {
        return mRestAdapter.newNote(accessToken, note);
    }

    public Observable<ResponseDataModel> updateRemoteNote(String accessToken, NoteItem note, int noteId) {
        return mRestAdapter.updateNotes(accessToken, note, noteId);
    }

    public Observable<ResponseDataModel> fetchNotes(String accessToken, String username) {
        return mRestAdapter.fetchNotes(accessToken, username);
    }

    public Observable<ResponseDataModel> logoutAccess(String accessToken) {
        return mRestAdapter.logoutAccess(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ResponseDataModel> logoutRefresh(String accessToken) {
        return mRestAdapter.logoutRefresh(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

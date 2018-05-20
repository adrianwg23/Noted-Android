package com.example.adrianwong.noted.util;

import android.content.Context;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.data.local.AppDatabase;
import com.example.adrianwong.noted.viewmodel.AddViewModelFactory;
import com.example.adrianwong.noted.viewmodel.ListViewModelFactory;

public class InjectorUtil {

    public static NotesRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        return NotesRepository.getIntance(database.noteDao(), NetworkHelper.getRestAdapter());
    }

    public static ListViewModelFactory provideListViewModelFactory(Context context) {
        NotesRepository repository = provideRepository(context.getApplicationContext());
        return new ListViewModelFactory(repository);
    }
    public static AddViewModelFactory provideAddViewModelFactory(Context context, int noteId) {
        NotesRepository repository = provideRepository(context.getApplicationContext());
        return new AddViewModelFactory(repository, noteId);
    }
}

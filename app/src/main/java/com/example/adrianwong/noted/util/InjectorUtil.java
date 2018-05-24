package com.example.adrianwong.noted.util;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.viewmodel.AddViewModelFactory;
import com.example.adrianwong.noted.viewmodel.ListViewModelFactory;

public class InjectorUtil {

    public static ListViewModelFactory provideListViewModelFactory(NotesRepository repository) {
        return new ListViewModelFactory(repository);
    }
    public static AddViewModelFactory provideAddViewModelFactory(NotesRepository repository, int noteId) {
        return new AddViewModelFactory(repository, noteId);
    }
}

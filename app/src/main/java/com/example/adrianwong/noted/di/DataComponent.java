package com.example.adrianwong.noted.di;

import com.example.adrianwong.noted.data.NotesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={DataModule.class})
public interface DataComponent {

    void inject(NotesRepository repository);
}

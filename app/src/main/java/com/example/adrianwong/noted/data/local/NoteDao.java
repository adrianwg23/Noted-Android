package com.example.adrianwong.noted.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY updated_at")
    LiveData<List<NoteItem>> loadAllTasks();

    @Insert
    void insertNote(NoteItem note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteItem note);

    @Delete
    void deleteNote(NoteItem note);

    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<NoteItem> loadTaskById(int id);

}

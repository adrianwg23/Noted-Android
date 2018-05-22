package com.example.adrianwong.noted.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.adrianwong.noted.datamodel.NoteItem;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE user_id = :id ORDER BY updated_at")
    LiveData<List<NoteItem>> loadAllTasks(int id);

    @Insert()
    void insertNote(NoteItem note);

    @Insert()
    void insertMultipleNotes(List<NoteItem> notes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteItem note);

    @Delete
    void deleteNote(NoteItem note);

    @Delete
    void deleteAll(List<NoteItem> notes);

    @Query("SELECT * FROM notes WHERE note_id = :id")
    LiveData<NoteItem> loadTaskById(int id);

}

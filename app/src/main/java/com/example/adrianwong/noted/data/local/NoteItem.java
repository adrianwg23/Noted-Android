package com.example.adrianwong.noted.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String noteTitle;
    private String noteBody;
    private String updatedAt;
    private int priority;

    @Ignore
    public NoteItem(String noteTitle, String noteBody, String updatedAt, int priority) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.updatedAt = updatedAt;
        this.priority = priority;
    }

    public NoteItem(int id, String noteTitle, String noteBody, String updatedAt, int priority) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.updatedAt = updatedAt;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getPriority() {
        return priority;
    }
}

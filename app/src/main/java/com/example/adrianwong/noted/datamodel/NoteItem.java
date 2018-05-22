package com.example.adrianwong.noted.datamodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Date;

@Entity(tableName = "notes")
public class NoteItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    @Json(name = "note_id")
    private int id;

    @ColumnInfo(name = "title")
    @Json(name = "title")
    private String noteTitle;

    @ColumnInfo(name = "note")
    @Json(name = "note")
    private String noteBody;

    @ColumnInfo(name = "priority")
    @Json(name = "priority")
    private int priority;

    @ColumnInfo(name = "updated_at")
    @Json(name = "updated_at")
    private Long updatedAt;

    @ColumnInfo(name = "user_id")
    @Json(name = "user_id")
    private int userId;

    @Ignore
    public NoteItem(String noteTitle, String noteBody, Long updatedAt, int priority, int userId) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.updatedAt = updatedAt;
        this.priority = priority;
        this.userId = userId;
    }

    public NoteItem(int id, String noteTitle, String noteBody, Long updatedAt, int priority, int userId) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.updatedAt = updatedAt;
        this.priority = priority;
        this.userId = userId;
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

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public int getPriority() {
        return priority;
    }

    public int getUserId() { return userId; }

    public void setId(int id) {
        this.id = id;
    }
}

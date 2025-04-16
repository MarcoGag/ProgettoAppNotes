package com.example.progettoappnotes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Note> getAllNotes();
}

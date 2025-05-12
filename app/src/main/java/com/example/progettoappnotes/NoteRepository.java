package com.example.progettoappnotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.progettoappnotes.Note;
import com.example.progettoappnotes.NoteDao;
import com.example.progettoappnotes.NotesDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private NoteDao noteDao;
    private List<Note> notes;

    ExecutorService executors = Executors.newSingleThreadExecutor();

    public NoteRepository(Application application){
        NotesDataBase database = NotesDataBase.getDataBase(application);
        noteDao = database.noteDao();
        notes = noteDao.getAllNotes();
    }
    public void insert(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insertNote(note);
            }
        });
    }
    /* update da fare in classe NoteDao
    public void update(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }
    */
    public void delete(Note note){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteNote(note);
            }
        });
    }
    public List<Note> getAllNotes(){
        return notes;
    }

}

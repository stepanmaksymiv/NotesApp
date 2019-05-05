package com.hfad.notesapp.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.hfad.notesapp.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;

//    public List<NoteEntity> mNotes;
    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
//        mNotes = SampleData.getNotesList();
        mDb = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDAO().insertAll(SampleData.getNotesList());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes(){
       return mDb.notesDAO().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDAO().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.notesDAO().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDAO().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.notesDAO().deleteNote(note);
            }
        });
    }
}

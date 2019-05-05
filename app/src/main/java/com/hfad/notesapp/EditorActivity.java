package com.hfad.notesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hfad.notesapp.database.NoteEntity;
import com.hfad.notesapp.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hfad.notesapp.utilities.Constants.EDITING_KEY;
import static com.hfad.notesapp.utilities.Constants.NOTE_KEY_ID;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;

    @BindView(R.id.edit_note_text)
    EditText mEditText;

    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if (noteEntity != null && !mEditing) {
                    mEditText.setText(noteEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New note");
            mNewNote = true;
        } else {
           setTitle("Edit note");
           int noteId = extras.getInt(NOTE_KEY_ID);
           mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            saveAndReturn();
            return true;
        } else if (id == R.id.action_delete_one){
            mViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mEditText.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, mEditing = true);
        super.onSaveInstanceState(outState);
    }
}

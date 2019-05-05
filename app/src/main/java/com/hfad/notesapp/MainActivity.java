package com.hfad.notesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hfad.notesapp.database.NoteEntity;
import com.hfad.notesapp.ui.NotesAdapter;
import com.hfad.notesapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    };

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
//        initViewModel();
        initRecyclerView();
        initViewModel();

//        notesData.addAll(SampleData.getNotesList());

//        notesData.addAll(mViewModel.mNotes);
//        for (NoteEntity note: notesData) {
//            Log.i("NotesApp", note.toString());
//        }
    }

    private void initViewModel() {

        Observer<List<NoteEntity>> notesObserverList = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntityList) {
                notesData.clear();
                if (noteEntityList != null) {
                    notesData.addAll(noteEntityList);
                }
                if (mAdapter == null) {
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.notifyDataSetChanged();
                }

            }
        };

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.mNotes.observe(this, notesObserverList);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);

//        mAdapter = new NotesAdapter(notesData, this);
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_data) {
            addSampleData();
            return true;
        }else if (id == R.id.action_delete_all){
            deleteAllNotes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}

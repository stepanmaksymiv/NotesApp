package com.hfad.notesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.notesapp.EditorActivity;
import com.hfad.notesapp.R;
import com.hfad.notesapp.database.NoteEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hfad.notesapp.utilities.Constants.NOTE_KEY_ID;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<NoteEntity> mNotes;
    private Context context;

    public NotesAdapter(List<NoteEntity> mNotes, Context context) {
        this.mNotes = mNotes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NoteEntity entity = mNotes.get(position);
        holder.mTextView.setText(entity.getText());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorActivity.class);
                intent.putExtra(NOTE_KEY_ID, entity.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.note_text)
        TextView mTextView;

        @BindView(R.id.float_btn)
        FloatingActionButton mFab;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

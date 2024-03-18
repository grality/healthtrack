package com.example.healthtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.database.NoteDatabaseHelper;
import com.example.healthtrack.models.Note;
import com.example.healthtrack.utils.SessionManager;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
    private NoteDatabaseHelper dbHelper;

    public NotesAdapter(Context context, List<Note> notes){
        super(context,0,notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Note note = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell,parent,false);
        }

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView desc = convertView.findViewById(R.id.cellDesc);
        TextView noteSets = convertView.findViewById(R.id.cellSets);
        TextView noteReps = convertView.findViewById(R.id.cellReps);

        dbHelper = new NoteDatabaseHelper(getContext());


        title.setText(note.getTitle());
        desc.setText(note.getDescription());
        noteSets.setText(note.getNombreSets());
        noteReps.setText(note.getNombreReps());

        ImageView deleteImageView = convertView.findViewById(R.id.TrashImageView);

        deleteImageView.setOnClickListener(v -> {
            Note noteToRemove = getItem(position);
            if (noteToRemove != null) {
                SessionManager sessionManager = new SessionManager(getContext());
                dbHelper.deleteNote(noteToRemove.getId(), sessionManager.getEmail());
                remove(noteToRemove);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}

package com.example.healthtrack.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.models.Note;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
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



        title.setText(note.getTitle());
        desc.setText(note.getDescription());
        noteSets.setText(note.getNombreSets());
        noteReps.setText(note.getNombreReps());

        return convertView;
    }
}

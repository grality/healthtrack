package com.example.healthtrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthtrack.R;
import com.example.healthtrack.database.NoteDatabaseHelper;
import com.example.healthtrack.models.Exercise;
import com.example.healthtrack.models.Note;
import com.example.healthtrack.Adapter.NotesAdapter;
import com.example.healthtrack.utils.SessionManager;

import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment {


    private ListView noteListView;
    private EditText editTextDesc;

    private EditText editTextSets;
    private NoteDatabaseHelper dbHelper;

    private EditText editTextReps;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new NoteDatabaseHelper(requireContext());
    }

    private void setNoteAdapter(int id) {
        SessionManager sessionManager = new SessionManager(getContext());
        List<Note> noteList = dbHelper.getAllNotes(sessionManager.getEmail(), id);
        Collections.reverse(noteList);
        NotesAdapter notesAdapter = new NotesAdapter(requireContext(), noteList);
        noteListView.setAdapter(notesAdapter);
    }

    public void newNote(String exerciceTitle) {
        String desc = editTextDesc.getText().toString();
        String sets = editTextSets.getText().toString();
        String reps = editTextReps.getText().toString();

        if (sets.isEmpty() || reps.isEmpty()) {
            Toast.makeText(getContext(), R.string.fill_set_and_reps, Toast.LENGTH_SHORT).show();
            return;
        }

        Note newNote = new Note(Exercise.getExerciseWithTitle(exerciceTitle).getId(), exerciceTitle, Exercise.getExerciseWithTitle(exerciceTitle), desc, sets, reps);
        SessionManager sessionManager = new SessionManager(getContext());
        dbHelper.addNote(newNote, sessionManager.getEmail());

        setNoteAdapter(Exercise.getExerciseWithTitle(exerciceTitle).getId());

        editTextDesc.setText("");
        editTextReps.setText("");
        editTextSets.setText("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        String exerciceTitle;
        if (getArguments() != null) {
            exerciceTitle = getArguments().getString("exercise_title", "");
        } else {
            exerciceTitle = null;
        }

        TextView noteTitle = rootView.findViewById(R.id.textNoteTitle);
        noteTitle.setText(exerciceTitle);

        noteListView = rootView.findViewById(R.id.notesListView);
        editTextDesc = rootView.findViewById(R.id.editTextDesc);
        editTextSets = rootView.findViewById(R.id.editTextNombreSets);
        editTextReps = rootView.findViewById(R.id.editTextNombreReps);
        Button noteSaveBtn = rootView.findViewById(R.id.noteSaveBtn);

        if (noteSaveBtn != null) {
            noteSaveBtn.setOnClickListener(v -> newNote(exerciceTitle));
        }

        setNoteAdapter(Exercise.getExerciseWithTitle(exerciceTitle).getId());
        return rootView;
    }


}
package com.example.healthtrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.healthtrack.R;
import com.example.healthtrack.models.Note;
import com.example.healthtrack.utils.NotesAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView noteListView;
    private EditText editTextTitle;
    private EditText editTextDesc;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void setNoteAdapter() {
        NotesAdapter notesAdapter = new NotesAdapter(requireContext(), Note.noteArrayList);
        noteListView.setAdapter(notesAdapter);
    }

    public void newNote(){
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();

        int nextId = Note.noteArrayList.size() + 1;
        Note newNote = new Note(nextId, title, desc);

        Note.noteArrayList.add(newNote);

        NotesAdapter notesAdapter = new NotesAdapter(getContext(), Note.noteArrayList);
        noteListView.setAdapter(notesAdapter);

        editTextTitle.setText("");
        editTextDesc.setText("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_notes,container,false);

        noteListView = rootView.findViewById(R.id.notesListView);
        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextDesc = rootView.findViewById(R.id.editTextDesc);
        Button noteSaveBtn = rootView.findViewById(R.id.noteSaveBtn);

        if (noteSaveBtn != null) {
            noteSaveBtn.setOnClickListener(v -> newNote());
        }

        setNoteAdapter();
        return rootView;
    }
}
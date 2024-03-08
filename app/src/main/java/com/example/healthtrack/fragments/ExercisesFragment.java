package com.example.healthtrack.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


import com.example.healthtrack.Adapter.ExerciseAdapter;
import com.example.healthtrack.R;
import com.example.healthtrack.models.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisesFragment extends Fragment {
    private List<Exercise> baseExercises;
    private List<Exercise> exercises;
    private String selectedMuscleType;


    public ExercisesFragment() {
        // Required empty public constructor
    }

    public static ExercisesFragment newInstance() {
        ExercisesFragment fragment = new ExercisesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercices, container, false);

        ListView listView = view.findViewById(R.id.listViewExercises);

        // Générer une liste d'exercices de test

        baseExercises= generateSampleExercises();
        exercises = new ArrayList<>(baseExercises);

        // Créer un adaptateur pour la liste d'exercices
        ExerciseAdapter adapter = new ExerciseAdapter(requireContext(), exercises);

        // Définir l'adaptateur sur la ListView
        listView.setAdapter(adapter);

        Spinner spinnerMuscleType = (Spinner) view.findViewById(R.id.spinner_categories);

        spinnerMuscleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedMuscleType = adapterView.getItemAtPosition(position).toString();
                Log.d("MuscleType", selectedMuscleType);

                if (!"Tous les muscles".equals(selectedMuscleType)) {
                    List<Exercise> filteredExercises = filterExercisesByMuscleType(baseExercises, selectedMuscleType);
                    adapter.updateList(filteredExercises);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Switch switchFavorite = (Switch) view.findViewById(R.id.switchFavorite);
        switchFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adapter.setShowFavoriteOnly(isChecked);
            Log.d("Switch", String.valueOf(isChecked));
            List<Exercise> filteredExercises = filterExercisesByMuscleType(adapter.getmFilteredExercises(), selectedMuscleType);
            adapter.updateList(filteredExercises);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
    private List<Exercise> filterExercisesByMuscleType(List<Exercise> allExercises, String muscleType) {
        List<Exercise> filteredExercises = new ArrayList<>();
        for (Exercise exercise : allExercises) {
            if (muscleType.equals(exercise.getMuscleType())) {
                filteredExercises.add(exercise);
            }
        }
        return filteredExercises;
    }

    // Méthode pour générer des exercices de test
    private List<Exercise> generateSampleExercises() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Squats", "Exercice pour les jambes", "Legs",R.drawable.squats,true));
        exercises.add(new Exercise("Squats", "Exercice pour les jambes", "Legs",R.drawable.squats,true));
        exercises.add(new Exercise("Push-ups", "Exercice pour les bras et les pectoraux", "Pecs", R.drawable.pecs_decline,true));
        exercises.add(new Exercise("Crunches", "Exercice pour les abdominaux", "Abs", R.drawable.crunch,false));
        return exercises;
    }
}
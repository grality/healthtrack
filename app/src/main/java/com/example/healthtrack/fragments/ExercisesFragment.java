package com.example.healthtrack.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.healthtrack.Adapter.ExerciseAdapter;
import com.example.healthtrack.R;
import com.example.healthtrack.database.FavorisDatabaseHelper;
import com.example.healthtrack.models.Exercise;
import com.example.healthtrack.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisesFragment extends Fragment {
    private List<Exercise> baseExercises;
    private List<Exercise> exercises;
    private String selectedMuscleType;

    private ExerciseAdapter adapter;


    public ExercisesFragment() {
        // Required empty public constructor
    }

    public static ExercisesFragment newInstance() {
        return new ExercisesFragment();
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
        baseExercises = generateSampleExercises();
        exercises = new ArrayList<>(baseExercises);

        // Créer un adaptateur pour la liste d'exercices
        adapter = new ExerciseAdapter(requireContext(), exercises);

        // Définir l'adaptateur sur la ListView
        listView.setAdapter(adapter);



        if (isTabletMode()) {
            Button buttonAll = view.findViewById(R.id.button_all);
            ImageButton buttonLegs = view.findViewById(R.id.button_legs);
            ImageButton buttonGlutes = view.findViewById(R.id.button_glutes);
            ImageButton buttonAbs = view.findViewById(R.id.button_abs);
            ImageButton buttonArms = view.findViewById(R.id.button_arms);
            ImageButton buttonPecs = view.findViewById(R.id.button_pecs);
            ImageButton buttonShoulders = view.findViewById(R.id.button_shoulders);

            buttonAll.setTag(getString(R.string.category_all));
            buttonLegs.setTag(getString(R.string.category_legs));
            buttonGlutes.setTag(getString(R.string.category_glutes));
            buttonAbs.setTag(getString(R.string.category_abs));
            buttonArms.setTag(getString(R.string.category_arms));
            buttonPecs.setTag(getString(R.string.category_pecs));
            buttonShoulders.setTag(getString(R.string.category_shoulders));



            buttonAll.setOnClickListener(this::onCategoryButtonClick);
            buttonLegs.setOnClickListener(this::onCategoryButtonClick);
            buttonGlutes.setOnClickListener(this::onCategoryButtonClick);
            buttonAbs.setOnClickListener(this::onCategoryButtonClick);
            buttonArms.setOnClickListener(this::onCategoryButtonClick);
            buttonPecs.setOnClickListener(this::onCategoryButtonClick);
            buttonShoulders.setOnClickListener(this::onCategoryButtonClick);

        } else {
            Spinner spinnerCategories = view.findViewById(R.id.spinner_categories);
            spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    selectedMuscleType = adapterView.getItemAtPosition(position).toString();
                    Log.d("MuscleType", selectedMuscleType);

                    if (!"Toutes les categories".equals(selectedMuscleType) && !"All the categories".equals(selectedMuscleType)) {
                        List<Exercise> filteredExercises = filterExercisesByMuscleType(baseExercises, selectedMuscleType);
                        adapter.updateList(filteredExercises);
                    } else {
                        adapter.updateList(baseExercises);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        Switch switchFavorite = view.findViewById(R.id.switchFavorite);
        switchFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Afficher uniquement les exercices favoris
                List<Exercise> favoriteExercises = getFavoriteExercises(exercises);
                adapter.updateList(favoriteExercises);
            } else {
                // Afficher tous les exercices
                if (!"Toutes les categories".equals(selectedMuscleType) && !"All the categories".equals(selectedMuscleType)) {
                    List<Exercise> filteredExercises = filterExercisesByMuscleType(baseExercises, selectedMuscleType);
                    adapter.updateList(filteredExercises);
                } else {
                    adapter.updateList(baseExercises);
                }
            }
        });

        return view;
    }

    private boolean isTabletMode() {
        // Obtenez la configuration actuelle de l'appareil
        Configuration configuration = getResources().getConfiguration();

        // Vérifiez si l'appareil est en mode tablette en examinant la taille de l'écran (screenLayout)
        int screenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        // Si l'écran est de taille XLARGE ou LARGE, alors nous sommes probablement en mode tablette
        return screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE ||
                screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void onCategoryButtonClick(View view) {
        String selectedMuscleType = (String) view.getTag();

        Log.d("selectedMuscleTypeButton", selectedMuscleType);
        if (!"Toutes les categories".equals(selectedMuscleType) && !"All the categories".equals(selectedMuscleType)) {
            List<Exercise> filteredExercises = filterExercisesByMuscleType(baseExercises, selectedMuscleType);
            adapter.updateList(filteredExercises);
        } else {
            adapter.updateList(baseExercises);
        }
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
        FavorisDatabaseHelper favorisDatabaseHelper = new FavorisDatabaseHelper(getContext());
        SessionManager sessionManager = new SessionManager(getContext());
        List<Integer> favoriteExerciseIds = favorisDatabaseHelper.getFavoriteExercises(sessionManager.getEmail());

        exercises.add(new Exercise("Squats", "Exercice pour les jambes", getString(R.string.category_legs),R.drawable.squats,favoriteExerciseIds.contains(1), 1));
        exercises.add(new Exercise("Push-ups", "Exercice pour les bras et les pectoraux", getString(R.string.category_pecs), R.drawable.pecs_decline,favoriteExerciseIds.contains(2), 2));
        exercises.add(new Exercise("Crunches", "Exercice pour les abdominaux", getString(R.string.category_abs), R.drawable.crunch,favoriteExerciseIds.contains(3),3));
        exercises.add(new Exercise("Développé Arnold Haltère", "Exercice pour les épaules", getString(R.string.category_shoulders), R.drawable.developpe_arnold_haltere_exercice_musculation, favoriteExerciseIds.contains(4), 4));
        exercises.add(new Exercise("Développé Couché Prise Inversée", "Exercice pour les pectoraux", getString(R.string.category_pecs), R.drawable.developpe_couche_prise_inversee_exercice_musculation, favoriteExerciseIds.contains(5), 5));
        exercises.add(new Exercise("Dips Barres Parallèles Triceps", "Exercice pour les triceps", getString(R.string.category_arms), R.drawable.dips_barres_paralleles_triceps_musculation, favoriteExerciseIds.contains(6), 6));
        exercises.add(new Exercise("Écartés Décliné avec Haltères", "Exercice pour les pectoraux", getString(R.string.category_pecs), R.drawable.ecartes_decline_avec_halteres, favoriteExerciseIds.contains(7), 7));
        exercises.add(new Exercise("Extensions des Mollets avec Partenaire", "Exercice pour les mollets", getString(R.string.category_legs), R.drawable.extensions_des_mollets_avec_partenaire_musculation, favoriteExerciseIds.contains(8), 8));
        exercises.add(new Exercise("Extension des Mollets Donkey", "Exercice pour les mollets", getString(R.string.category_legs), R.drawable.extension_des_mollets_donkey_musculation, favoriteExerciseIds.contains(9), 9));
        exercises.add(new Exercise("Face Pull", "Exercice pour les épaules", getString(R.string.category_shoulders), R.drawable.face_pull_exercice_musculation_epaules, favoriteExerciseIds.contains(10), 10));
        exercises.add(new Exercise("Good Morning Ischio Cuisses Fessiers", "Exercice pour les ischio-jambiers et les fessiers", getString(R.string.category_legs), R.drawable.good_morning_ischio_cuisses_fessiers_musculation, favoriteExerciseIds.contains(11), 11));
        exercises.add(new Exercise("Leg Curl Allongé Cuisses Ischios", "Exercice pour les cuisses et les ischio-jambiers", getString(R.string.category_legs), R.drawable.leg_curl_allonge_cuisses_ischios_musculation, favoriteExerciseIds.contains(12), 12));
        exercises.add(new Exercise("Pecs Décliné", "Exercice pour les pectoraux", getString(R.string.category_pecs), R.drawable.pecs_decline, favoriteExerciseIds.contains(13), 13));
        exercises.add(new Exercise("Presse à Cuisses Inclinée", "Exercice pour les cuisses", getString(R.string.category_legs), R.drawable.presse_a_cuisses_inclinee_musculation, favoriteExerciseIds.contains(14), 14));
        exercises.add(new Exercise("Relevé Jambes Chaise Romaine Abdominaux", "Exercice pour les abdominaux", getString(R.string.category_abs), R.drawable.releve_jambes_chaise_romaine_abdominaux_musculation, favoriteExerciseIds.contains(15), 15));
        exercises.add(new Exercise("Roulette Abdominaux", "Exercice pour les abdominaux", getString(R.string.category_abs), R.drawable.roulette_abdominaux_musculation, favoriteExerciseIds.contains(16), 16));
        return exercises;
    }



    // Méthode pour obtenir les exercices favoris
    private List<Exercise> getFavoriteExercises(List<Exercise> exercises) {
        List<Exercise> favoriteExercises = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (exercise.isFavorite()) {
                favoriteExercises.add(exercise);
            }
        }
        return favoriteExercises;
    }
}
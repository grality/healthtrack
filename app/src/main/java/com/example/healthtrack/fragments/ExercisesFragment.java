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

public class ExercisesFragment extends Fragment {
    private List<Exercise> baseExercises;
    private List<Exercise> exercises;
    private String selectedMuscleType;

    private ExerciseAdapter adapter;


    public ExercisesFragment() {
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
            ///Initialisation des items
            Button buttonAll = view.findViewById(R.id.button_all);
            ImageButton buttonLegs = view.findViewById(R.id.button_legs);
            ImageButton buttonGlutes = view.findViewById(R.id.button_glutes);
            ImageButton buttonAbs = view.findViewById(R.id.button_abs);
            ImageButton buttonArms = view.findViewById(R.id.button_arms);
            ImageButton buttonPecs = view.findViewById(R.id.button_pecs);
            ImageButton buttonShoulders = view.findViewById(R.id.button_shoulders);

            ///SetTag pour reperer les différents boutons en mode tablette
            buttonAll.setTag(getString(R.string.category_all));
            buttonLegs.setTag(getString(R.string.category_legs));
            buttonGlutes.setTag(getString(R.string.category_glutes));
            buttonAbs.setTag(getString(R.string.category_abs));
            buttonArms.setTag(getString(R.string.category_arms));
            buttonPecs.setTag(getString(R.string.category_pecs));
            buttonShoulders.setTag(getString(R.string.category_shoulders));

            /// Listners vers onCategoryButtonClick de chaque ImageButtons
            buttonAll.setOnClickListener(this::onCategoryButtonClick);
            buttonLegs.setOnClickListener(this::onCategoryButtonClick);
            buttonGlutes.setOnClickListener(this::onCategoryButtonClick);
            buttonAbs.setOnClickListener(this::onCategoryButtonClick);
            buttonArms.setOnClickListener(this::onCategoryButtonClick);
            buttonPecs.setOnClickListener(this::onCategoryButtonClick);
            buttonShoulders.setOnClickListener(this::onCategoryButtonClick);

            adapter.updateList(baseExercises);

        } else {

            Spinner spinnerCategories = view.findViewById(R.id.spinner_categories);
            spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    ///Muscle choisi dans le spinner
                    selectedMuscleType = adapterView.getItemAtPosition(position).toString();
                    Log.d("MuscleType", selectedMuscleType);

                    ///Si ce n'est pas toutes catégorie faire un tri
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

        // Récupérez la taille de l'écran et l'orientation
        int screenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = configuration.orientation;

        // Vérifiez si l'appareil est en mode tablette en examinant la taille de l'écran (screenLayout)
        // et l'orientation de l'écran
        return (screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE ||
                screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE) ||
                orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    private void onCategoryButtonClick(View view) {
        ///Recuperer le tag des ImageButtons
        String selectedMuscleType = (String) view.getTag();

        Log.d("selectedMuscleTypeButton", selectedMuscleType);
        //Si ce n'est pas toutes catégorie faire un tri
        if (!"Toutes les categories".equals(selectedMuscleType) && !"All the categories".equals(selectedMuscleType)) {
            List<Exercise> filteredExercises = filterExercisesByMuscleType(baseExercises, selectedMuscleType);
            adapter.updateList(filteredExercises);
        } else {
            adapter.updateList(baseExercises);
        }
    }


    private List<Exercise> filterExercisesByMuscleType(List<Exercise> allExercises, String muscleType) {
        ///Fonction les exercices de l'adapter à partir de parametre muscleType

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

        exercises.add(new Exercise(getString(R.string.exercise_squats), getString(R.string.exercise_squats_description), getString(R.string.category_legs), R.drawable.squats, favoriteExerciseIds.contains(1), 1));
        exercises.add(new Exercise(getString(R.string.exercise_push_ups), getString(R.string.exercise_push_ups_description), getString(R.string.category_pecs), R.drawable.pecs_decline, favoriteExerciseIds.contains(2), 2));
        exercises.add(new Exercise(getString(R.string.exercise_crunches), getString(R.string.exercise_crunches_description), getString(R.string.category_abs), R.drawable.crunch, favoriteExerciseIds.contains(3), 3));
        exercises.add(new Exercise(getString(R.string.exercise_developpe_arnold_haltere), getString(R.string.exercise_developpe_arnold_haltere_description), getString(R.string.category_shoulders), R.drawable.developpe_arnold_haltere_exercice_musculation, favoriteExerciseIds.contains(4), 4));
        exercises.add(new Exercise(getString(R.string.exercise_developpe_couche_prise_inversee), getString(R.string.exercise_developpe_couche_prise_inversee_description), getString(R.string.category_pecs), R.drawable.developpe_couche_prise_inversee_exercice_musculation, favoriteExerciseIds.contains(5), 5));
        exercises.add(new Exercise(getString(R.string.exercise_dips_barres_paralleles_triceps), getString(R.string.exercise_dips_barres_paralleles_triceps_description), getString(R.string.category_arms), R.drawable.dips_barres_paralleles_triceps_musculation, favoriteExerciseIds.contains(6), 6));
        exercises.add(new Exercise(getString(R.string.exercise_ecartes_decline_avec_halteres), getString(R.string.exercise_ecartes_decline_avec_halteres_description), getString(R.string.category_pecs), R.drawable.ecartes_decline_avec_halteres, favoriteExerciseIds.contains(7), 7));
        exercises.add(new Exercise(getString(R.string.exercise_extensions_des_mollets_avec_partenaire), getString(R.string.exercise_extensions_des_mollets_avec_partenaire_description), getString(R.string.category_legs), R.drawable.extensions_des_mollets_avec_partenaire_musculation, favoriteExerciseIds.contains(8), 8));
        exercises.add(new Exercise(getString(R.string.exercise_extension_des_mollets_donkey), getString(R.string.exercise_extension_des_mollets_donkey_description), getString(R.string.category_legs), R.drawable.extension_des_mollets_donkey_musculation, favoriteExerciseIds.contains(9), 9));
        exercises.add(new Exercise(getString(R.string.exercise_face_pull), getString(R.string.exercise_face_pull_description), getString(R.string.category_shoulders), R.drawable.face_pull_exercice_musculation_epaules, favoriteExerciseIds.contains(10), 10));
        exercises.add(new Exercise(getString(R.string.exercise_good_morning_ischio_cuisses_fessiers), getString(R.string.exercise_good_morning_ischio_cuisses_fessiers_description), getString(R.string.category_legs), R.drawable.good_morning_ischio_cuisses_fessiers_musculation, favoriteExerciseIds.contains(11), 11));
        exercises.add(new Exercise(getString(R.string.exercise_leg_curl_allonge_cuisses_ischios), getString(R.string.exercise_leg_curl_allonge_cuisses_ischios_description), getString(R.string.category_legs), R.drawable.leg_curl_allonge_cuisses_ischios_musculation, favoriteExerciseIds.contains(12), 12));
        exercises.add(new Exercise(getString(R.string.exercise_pecs_decline), getString(R.string.exercise_pecs_decline_description), getString(R.string.category_pecs), R.drawable.pecs_decline, favoriteExerciseIds.contains(13), 13));
        exercises.add(new Exercise(getString(R.string.exercise_presse_a_cuisses_inclinee), getString(R.string.exercise_presse_a_cuisses_inclinee_description), getString(R.string.category_legs), R.drawable.presse_a_cuisses_inclinee_musculation, favoriteExerciseIds.contains(14), 14));
        exercises.add(new Exercise(getString(R.string.exercise_releve_jambes_chaise_romaine_abdominaux), getString(R.string.exercise_releve_jambes_chaise_romaine_abdominaux_description), getString(R.string.category_abs), R.drawable.releve_jambes_chaise_romaine_abdominaux_musculation, favoriteExerciseIds.contains(15), 15));
        exercises.add(new Exercise(getString(R.string.exercise_roulette_abdominaux), getString(R.string.exercise_roulette_abdominaux_description), getString(R.string.category_abs), R.drawable.roulette_abdominaux_musculation, favoriteExerciseIds.contains(16), 16));
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
package com.example.healthtrack.models;

import com.example.healthtrack.R;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private int id;
    private String title;
    private String description;
    private String muscleType;
    private int imageResource;
    private boolean favorite;

    private static final List<Exercise> exercises = new ArrayList<>();

    static {
        exercises.add(new Exercise("Squats", "Exercise for legs", "Legs", R.drawable.squats, false, 1));
        exercises.add(new Exercise("Push-ups", "Exercise for arms and chest", "Pecs", R.drawable.pecs_decline, false, 2));
        exercises.add(new Exercise("Crunches", "Exercise for abdominals", "Abs", R.drawable.crunch, false, 3));
        exercises.add(new Exercise("Dumbbell Arnold Press", "Exercise for shoulders", "Shoulders", R.drawable.developpe_arnold_haltere_exercice_musculation, false, 4));
        exercises.add(new Exercise("Incline Bench Press", "Exercise for chest", "Pecs", R.drawable.developpe_couche_prise_inversee_exercice_musculation, false, 5));
        exercises.add(new Exercise("Parallel Bar Dips", "Exercise for triceps", "Arms", R.drawable.dips_barres_paralleles_triceps_musculation, false, 6));
        exercises.add(new Exercise("Decline Dumbbell Flyes", "Exercise for chest", "Pecs", R.drawable.ecartes_decline_avec_halteres, false, 7));
        exercises.add(new Exercise("Calf Raises with Partner", "Exercise for calves", "Legs", R.drawable.extensions_des_mollets_avec_partenaire_musculation, false, 8));
        exercises.add(new Exercise("Donkey Calf Raises", "Exercise for calves", "Legs", R.drawable.extension_des_mollets_donkey_musculation, false, 9));
        exercises.add(new Exercise("Face Pull", "Exercise for shoulders", "Shoulders", R.drawable.face_pull_exercice_musculation_epaules, false, 10));
        exercises.add(new Exercise("Good Morning", "Exercise for hamstrings and glutes", "Legs", R.drawable.good_morning_ischio_cuisses_fessiers_musculation, false, 11));
        exercises.add(new Exercise("Lying Leg Curls", "Exercise for thighs and hamstrings", "Legs", R.drawable.leg_curl_allonge_cuisses_ischios_musculation, false, 12));
        exercises.add(new Exercise("Decline Bench Press", "Exercise for chest", "Pecs", R.drawable.pecs_decline, false, 13));
        exercises.add(new Exercise("Incline Leg Press", "Exercise for thighs", "Legs", R.drawable.presse_a_cuisses_inclinee_musculation, false, 14));
        exercises.add(new Exercise("Leg Raises", "Exercise for abdominals", "Abs", R.drawable.releve_jambes_chaise_romaine_abdominaux_musculation, false, 15));
        exercises.add(new Exercise("Ab Roller", "Exercise for abdominals", "Abs", R.drawable.roulette_abdominaux_musculation, false, 16));


    }

    public Exercise(String title, String description, String muscleType, int imageResource, boolean favorite, int id) {
        this.title = title;
        this.description = description;
        this.muscleType = muscleType;
        this.imageResource = imageResource;
        this.favorite = favorite;
        this.id = id;
    }

    public Exercise() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscleType() {
        return muscleType;
    }

    public void setMuscleType(String muscleType) {
        this.muscleType = muscleType;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Exercise getExerciseWithTitle(String title) {
        for (Exercise exercise : exercises) {
            if (exercise.getTitle().equals(title)) {
                return exercise;
            }
        }
        return null;
    }
}

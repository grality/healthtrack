package com.example.healthtrack.models;

import android.graphics.drawable.Drawable;

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
        exercises.add(new Exercise("Squats", "Exercice pour les jambes", "Legs", R.drawable.squats, false, 1));
        exercises.add(new Exercise("Push-ups", "Exercice pour les bras et les pectoraux", "Pecs", R.drawable.pecs_decline, false, 2));
        exercises.add(new Exercise("Crunches", "Exercice pour les abdominaux", "Abs", R.drawable.crunch, false, 3));
        exercises.add(new Exercise("Développé Arnold Haltère", "Exercice pour les épaules", "Shoulders", R.drawable.developpe_arnold_haltere_exercice_musculation, false, 4));
        exercises.add(new Exercise("Développé Couché Prise Inversée", "Exercice pour les pectoraux", "Pecs", R.drawable.developpe_couche_prise_inversee_exercice_musculation, false, 5));
        exercises.add(new Exercise("Dips Barres Parallèles Triceps", "Exercice pour les triceps", "Arms", R.drawable.dips_barres_paralleles_triceps_musculation, false, 6));
        exercises.add(new Exercise("Écartés Décliné avec Haltères", "Exercice pour les pectoraux", "Pecs", R.drawable.ecartes_decline_avec_halteres, false, 7));
        exercises.add(new Exercise("Extensions des Mollets avec Partenaire", "Exercice pour les mollets", "Legs", R.drawable.extensions_des_mollets_avec_partenaire_musculation, false, 8));
        exercises.add(new Exercise("Extension des Mollets Donkey", "Exercice pour les mollets", "Legs", R.drawable.extension_des_mollets_donkey_musculation, false, 9));
        exercises.add(new Exercise("Face Pull", "Exercice pour les épaules", "Shoulders", R.drawable.face_pull_exercice_musculation_epaules, false, 10));
        exercises.add(new Exercise("Good Morning Ischio Cuisses Fessiers", "Exercice pour les ischio-jambiers et les fessiers", "Legs", R.drawable.good_morning_ischio_cuisses_fessiers_musculation, false, 11));
        exercises.add(new Exercise("Leg Curl Allongé Cuisses Ischios", "Exercice pour les cuisses et les ischio-jambiers", "Legs", R.drawable.leg_curl_allonge_cuisses_ischios_musculation, false, 12));
        exercises.add(new Exercise("Pecs Décliné", "Exercice pour les pectoraux", "Pecs", R.drawable.pecs_decline, false, 13));
        exercises.add(new Exercise("Presse à Cuisses Inclinée", "Exercice pour les cuisses", "Legs", R.drawable.presse_a_cuisses_inclinee_musculation, false, 14));
        exercises.add(new Exercise("Relevé Jambes Chaise Romaine Abdominaux", "Exercice pour les abdominaux", "Abs", R.drawable.releve_jambes_chaise_romaine_abdominaux_musculation, false, 15));
        exercises.add(new Exercise("Roulette Abdominaux", "Exercice pour les abdominaux", "Abs", R.drawable.roulette_abdominaux_musculation, false, 16));
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

    public static List<Exercise> getAllExercises() {
        return exercises;
    }
}

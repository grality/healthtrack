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

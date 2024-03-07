package com.example.healthtrack.models;

public class Exercise {
    private String title;
    private String description;
    private String muscleType;
    private int imageResource;

    private boolean favorite;

    public Exercise(String title, String description, String muscleType, int imageResource, boolean favorite) {
        this.title = title;
        this.description = description;
        this.muscleType = muscleType;
        this.imageResource = imageResource;
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMuscleType() {
        return muscleType;
    }

    public int getImageResource() {
        return imageResource;
    }
}

package com.example.healthtrack.models;

public class Exercise {
    private String title;
    private String description;
    private String muscleType;
    private int imageResource;

    public Exercise(String title, String description, String muscleType, int imageResource) {
        this.title = title;
        this.description = description;
        this.muscleType = muscleType;
        this.imageResource = imageResource;
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

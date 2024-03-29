package com.example.healthtrack.models;

import android.graphics.Bitmap;

public class Photo {
    private int id; // Identifiant de la progression
    private Bitmap photo; // Image de la progression
    private String description; // Description de la progression
    private String date; // Date de la progression

    public Photo(Bitmap photo, String description, String date) {
        this.photo = photo;
        this.description = description;
        this.date = date;
    }

    public Photo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


package com.example.healthtrack.models;

import java.util.Date;

public class Note {

    private int id;
    private String title;
    private String description;
    private Date deleted;

    private Exercise exercise;

    private String nombreSets;

    private String nombreReps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Note(int id, String title, String description, Exercise exercise, String nombreSets, String nombreReps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = null;
        this.exercise = exercise;
        this.nombreSets = nombreSets;
        this.nombreReps = nombreReps;
    }

    public Note() {

    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    public Note(int id, String title, Exercise exercise, String description, String nombreSets, String nombreReps) {
        this.id = id;
        this.title = title;
        this.exercise = exercise;
        this.description = description;
        this.nombreSets = nombreSets;
        this.nombreReps = nombreReps;
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

    public String getNombreSets() {
        return nombreSets;
    }

    public void setNombreSets(String nombreSets) {
        this.nombreSets = nombreSets;
    }

    public String getNombreReps() {
        return nombreReps;
    }

    public void setNombreReps(String nombreReps) {
        this.nombreReps = nombreReps;
    }
}

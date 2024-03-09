package com.example.healthtrack.models;

import java.util.ArrayList;
import java.util.Date;

public class Note {

    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    private int id ;
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

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    public Note(int id, String title, String description, Exercise exercise) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.exercise = exercise;
        deleted = null;
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

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}

package com.example.healthtrack.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthtrack.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "exercise_db";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_ID = "id";

    private static final String TABLE_EXERCISE = "exercise";
    private static final String KEY_TITLE = "title";

    private static final String KEY_DESCRITPION = "description";

    private static final String KEY_MUSCLETYPE = "muscle_type";

    private static final String KEY_IMAGERESSOURCE = "image_ressource";

    private static final String KEY_FAVORITE = "favorite";


    public ExerciseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "("
                + KEY_ID + "INTEGER PRIMARY KEY,"
                + KEY_TITLE + "TEXT ,"
                + KEY_DESCRITPION + "TEXT , "
                + KEY_MUSCLETYPE + "TEXT ,"
                + KEY_IMAGERESSOURCE + " INTEGER , "
                + KEY_FAVORITE + "BOOLEAN " + ")";
        db.execSQL(CREATE_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }

    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, exercise.getTitle());
        values.put(KEY_DESCRITPION, exercise.getDescription());
        values.put(KEY_MUSCLETYPE, exercise.getMuscleType());
        values.put(KEY_IMAGERESSOURCE, exercise.getImageResource());
        values.put(KEY_FAVORITE, exercise.isFavorite());
        db.insert(TABLE_EXERCISE, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                exercise.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                exercise.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRITPION)));
                exercise.setMuscleType(cursor.getString(cursor.getColumnIndex(KEY_MUSCLETYPE)));
                exercise.setImageResource(cursor.getInt(cursor.getColumnIndex(KEY_IMAGERESSOURCE)));
                exercise.setFavorite(cursor.getInt(cursor.getColumnIndex(KEY_FAVORITE)) != 0);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exerciseList;
    }

    public void deleteExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISE, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


}

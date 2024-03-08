package com.example.healthtrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FavorisDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorisManager";
    private static final String TABLE_FAVORIS = "favoris";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EXERCISE_ID = "exercise_id";

    public FavorisDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORIS_TABLE = "CREATE TABLE " + TABLE_FAVORIS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EMAIL + " TEXT,"
                + KEY_EXERCISE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_FAVORIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        onCreate(db);
    }

    public void addFavoriteExercise(String email, int exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_EXERCISE_ID, exerciseId);

        db.insert(TABLE_FAVORIS, null, values);
        db.close();
    }

    public void removeFavoriteExercise(String email, int exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORIS, KEY_EMAIL + " = ? AND " + KEY_EXERCISE_ID + " = ?",
                new String[]{email, String.valueOf(exerciseId)});
        db.close();
    }

    public List<Integer> getFavoriteExercises(String email) {
        List<Integer> favoriteExercises = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_EXERCISE_ID + " FROM " + TABLE_FAVORIS
                + " WHERE " + KEY_EMAIL + " = '" + email + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                favoriteExercises.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteExercises;
    }
}

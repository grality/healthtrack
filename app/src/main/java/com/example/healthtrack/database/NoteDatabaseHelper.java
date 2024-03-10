package com.example.healthtrack.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthtrack.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "note_db";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_EXERCISE_ID = "exercise_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_SETS = "sets";
    private static final String KEY_REPS = "reps";
    private static final String KEY_EMAIL = "email";

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EXERCISE_ID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_SETS + " TEXT,"
                + KEY_REPS + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void addNote(Note note, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, note.getId());
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getDescription());
        values.put(KEY_SETS, note.getNombreSets());
        values.put(KEY_REPS, note.getNombreReps());
        values.put(KEY_EMAIL, userEmail);
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }


    @SuppressLint("Range")
    public List<Note> getAllNotes(String userEmail, int exerciseId) {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + KEY_EMAIL + " = ? AND " + KEY_EXERCISE_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail, String.valueOf(exerciseId)});
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
                note.setNombreReps(cursor.getString(cursor.getColumnIndex(KEY_REPS)));
                note.setNombreSets(cursor.getString(cursor.getColumnIndex(KEY_SETS)));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return noteList;
    }

    public void deleteNote(int id, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ? AND " + KEY_EMAIL + " = ?",
                new String[]{String.valueOf(id), userEmail});
        db.close();
    }
}

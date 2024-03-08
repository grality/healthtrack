package com.example.healthtrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthtrack.models.Photo;
import com.example.healthtrack.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PhotoDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photo_db";
    private static final String TABLE_PHOTOS = "photos";
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image_data";
    private static final String KEY_DATE = "date";

    public PhotoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE + " BLOB,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_PHOTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    public void addPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, photo.getDescription());
        values.put(KEY_IMAGE, Utils.getBytes(photo.getPhoto()));
        values.put(KEY_DATE, photo.getDate());
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    public List<Photo> getAllPhotos() {
        List<Photo> photoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PHOTOS + " ORDER BY " + KEY_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.setId(cursor.getInt(0));
                photo.setDescription(cursor.getString(1));
                photo.setPhoto(Utils.getImage(cursor.getBlob(2)));
                photo.setDate(cursor.getString(3));
                photoList.add(photo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return photoList;
    }

    public void deletePhoto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHOTOS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

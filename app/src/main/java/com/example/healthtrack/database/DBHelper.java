package com.example.healthtrack.database;

import static com.example.healthtrack.utils.Utils.hashPassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.healthtrack.models.User;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "healthtrack.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "email TEXT," +
                        "username TEXT," +
                        "password TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Méthode pour récupérer un utilisateur en fonction de l'e-mail
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "email",
                "username",
                "password"
        };

        String selection = "email=?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                "users",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int emailIndex = cursor.getColumnIndex("email");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");

            int id = cursor.getInt(idIndex);
            String username = cursor.getString(usernameIndex);
            String password = cursor.getString(passwordIndex);

            user = new User(id, email, username, password);
            cursor.close();
        }

        return user;
    }

    public boolean checkPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"password"};
        String selection = "email=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                "users",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String storedPasswordHash = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return storedPasswordHash.equals(hashPassword(password));
        }

        return false;
    }

}

package com.example.healthtrack.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setLocale(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        Context contextWithLocale = context.createConfigurationContext(configuration);
        Resources resources = contextWithLocale.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static boolean isMoreThan24HoursAgo(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        try {
            Date pastDate = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pastDate); // Utilisez la date passée comme référence
            calendar.add(Calendar.HOUR_OF_DAY, 24); // Ajoutez 24 heures à la date passée
            Date twentyFourHoursLater = calendar.getTime(); // Obtenez la nouvelle date
            return twentyFourHoursLater.before(new Date()); // Vérifiez si la nouvelle date est avant la date actuelle
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


}

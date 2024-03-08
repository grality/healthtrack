package com.example.healthtrack.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.healthtrack.MainActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.database.PhotoDatabaseHelper;

import java.util.Calendar;

public class CheckImageService extends Service {
    private static final int NOTIFICATION_ID = 12345;
    private static final String CHANNEL_ID = "ImageCheckChannel";

    public CheckImageService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkImages();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkImages() {
        PhotoDatabaseHelper dbHelper = new PhotoDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        long twentyFourHoursAgo = calendar.getTimeInMillis();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM photos WHERE date > ?", new String[]{String.valueOf(twentyFourHoursAgo)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        if (count == 0) {
            sendNotification();
        }
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Image Check Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText("I hope you made your sport today")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}

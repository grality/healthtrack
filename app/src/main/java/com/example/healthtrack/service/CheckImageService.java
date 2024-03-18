package com.example.healthtrack.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.healthtrack.R;
import com.example.healthtrack.utils.Notifications;
import com.example.healthtrack.utils.SessionManager;
import com.example.healthtrack.utils.Utils;
import com.example.healthtrack.database.PhotoDatabaseHelper;

import java.util.Timer;
import java.util.TimerTask;

public class CheckImageService extends Service {

    private static final String TAG = "CheckImageService";
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service started");
        startTimer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Service destroyed");
        stopTimer();
        super.onDestroy();
    }

    private void startTimer() {
        Log.d(TAG, "startTimer: Timer started");
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                PhotoDatabaseHelper photo = new PhotoDatabaseHelper(getApplicationContext());
                String email = sessionManager.getEmail();

                if (email != null && !email.equals("guest")) {
                    String lastPhotoDate = photo.getLastPhotoDate(email);
                    if (lastPhotoDate != null && Utils.isMoreThan24HoursAgo(lastPhotoDate)) {
                        Log.d(TAG, "run: Dernière photo prise il y a plus de 24 heures");
                        Notifications.displayNotification(getApplicationContext(), getString(R.string.remind), getString(R.string.remind_evolution));
                    } else {
                        Log.d(TAG, "run: Dernière photo prise il y a moins de 24 heures");
                    }
                }
            }
        };

        long delay = 24 * 60 * 60 * 1000;
        timer.schedule(timerTask, delay, delay);
    }


    private void stopTimer() {
        Log.d(TAG, "stopTimer: Timer stopped");
        if (timer != null) {
            timer.cancel();
        }
    }
}

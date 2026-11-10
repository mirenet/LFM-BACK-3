package com.webhtml.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class MediaService extends Service {

    private static final String CHANNEL_ID = "MediaServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Kreiramo traženu notifikaciju da bi Android znao da aplikacija radi u prvom planu
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Web Aplikacija")
                .setContentText("Plejer radi u pozadini...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build();

        // Povezujemo servis sa notifikacijom (ovo štiti proces od gašenja)
        startForeground(1, notification);

        // Vraćamo START_STICKY da se automatski vrati ako ga sistem očisti
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Media Playback Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}

package com.webhtml.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
        // Kreiramo intent koji vraća korisnika u MainActivity kada klikne na notifikaciju
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        int flagsPending = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M 
                ? PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT 
                : PendingIntent.FLAG_UPDATE_CURRENT;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flagsPending);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("LFM APP")
                .setContentText("Working in background...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentIntent(pendingIntent) // <--- OVO VRAĆA U APLIKACIJU
                .setAutoCancel(true)
                .build();

        startForeground(1, notification);

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

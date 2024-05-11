package com.example.vizoralejelento;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class Notification {
    private static final String CHANNEL_ID = "vizora_ertesito";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager manager;
    private Context context;

    public Notification(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChanel();
    }

    private void createChanel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Vizora_ertesit",NotificationManager.IMPORTANCE_HIGH);

        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        channel.enableVibration(true);
        channel.setDescription("Ertesites a vizora lejelentesrol!");

        manager.createNotificationChannel(channel);
    }

    public void send(String message){
        Intent intent = new Intent(context, Diktalas.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,intent,PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("VizoraLejelento App")
                .setContentText(message)
                .setSmallIcon(R.drawable.water_drop)
                .setContentIntent(pendingIntent);

        this.manager.notify(NOTIFICATION_ID,builder.build());
    }

    public void cancel() {
        manager.cancel(NOTIFICATION_ID);
    }
}

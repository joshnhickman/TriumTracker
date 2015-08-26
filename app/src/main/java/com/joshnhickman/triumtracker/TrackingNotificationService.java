package com.joshnhickman.triumtracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Actor;

public class TrackingNotificationService {

    public static final int NOTIFICATION_ID = 7777;

    public static void updateNotification(Context context, Actor... actors) {
        if (actors == null || actors.length == 0) {
            throw new IllegalArgumentException("must have at least one Actor");
        }
        // base notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(actors[0].toString())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(Notification.CATEGORY_STATUS);
        if (actors.length > 1) builder.setContentText(actors[1].toString());

        // extended view
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle(actors[0].toString());
        for (int i = 1; i < actors.length; i++) style.addLine(actors[i].toString());
        builder.setStyle(style);

        // add the next button
        Intent nextActorIntent = new Intent(context, NextTurnReceiver.class);
        PendingIntent nextPendingIntent =
                PendingIntent.getBroadcast(context, 0, nextActorIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_action_upload, "NEXT", nextPendingIntent);

        // intent to use when clicked
        Intent resultIntent = new Intent(context, TriumTracker.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(TriumTracker.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}

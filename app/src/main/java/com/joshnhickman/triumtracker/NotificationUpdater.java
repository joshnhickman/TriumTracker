package com.joshnhickman.triumtracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.joshnhickman.triumtracker.domain.Actor;

public class NotificationUpdater {

    public static void updateNotification(Context context, Actor currentActor, Actor... actors) {
        // base notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle(currentActor.toString())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(Notification.CATEGORY_STATUS)
                .setOngoing(true);
        if (actors.length > 1) builder.setContentText(actors[1].toString());

        // extended view
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle(currentActor.toString());
        for (Actor actor : actors) if (actor != null) style.addLine(actor.toString());
        builder.setStyle(style);

        // add the next button
        Intent nextActorIntent = new Intent(context, NextTurnReceiver.class);
        PendingIntent nextPendingIntent =
                PendingIntent.getBroadcast(context, 0, nextActorIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_play_arrow, "NEXT", nextPendingIntent);

        // intent to use when clicked
//        Intent resultIntent = new Intent(context, TriumTracker.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(TriumTracker.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);

        // send notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Globals.NOTIFICATION_ID, builder.build());
    }

    public static void stopNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Globals.NOTIFICATION_ID);
    }

}

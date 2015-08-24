package com.joshnhickman.triumtracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Tracker;

public class Globals {

    public static final int NOTIFICATION_ID = 7777;

    public static ListAdapter listAdapter;
    public static Tracker tracker;

    public static void updateNotification(Context context) {
        // base notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Trium Tracker")
                .setContentText("CURRENT: " + tracker.getCurrentActor().toString())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(Notification.CATEGORY_STATUS);

        // extended view
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle("Trium Tracker");
        style.addLine("CURRENT: " + tracker.getCurrentActor().toString());
        if (tracker.getNextTurn() != null) {
            style.addLine("NEXT: " + tracker.getNextTurn().toString());
        }
        builder.setStyle(style);

        // add the next button
        Intent nextActorIntent = new Intent(context, NextTurnReceiver.class);
        nextActorIntent.putExtra("tracker", tracker);
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

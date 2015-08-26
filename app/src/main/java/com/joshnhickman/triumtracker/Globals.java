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

}

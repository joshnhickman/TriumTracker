package com.joshnhickman.triumtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Tracker;

public class NextActorReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "next actor!", Toast.LENGTH_SHORT).show();
        Globals.tracker.nextActor();
        Globals.updateNotification(context);
    }

}

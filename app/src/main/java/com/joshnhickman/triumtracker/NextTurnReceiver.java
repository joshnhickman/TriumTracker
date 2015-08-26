package com.joshnhickman.triumtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NextTurnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Globals.tracker.nextTurn();
        NotificationUpdater.updateNotification(context,
                Globals.tracker.getCurrentActor(),
                Globals.tracker.getNextActors(1));
    }

}

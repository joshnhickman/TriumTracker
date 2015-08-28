package com.joshnhickman.triumtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.joshnhickman.triumtracker.control.Combat;
import com.joshnhickman.triumtracker.control.NotificationUpdater;

public class NextTurnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Combat.nextTurn();
    }

}

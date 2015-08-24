package com.joshnhickman.triumtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NextTurnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Globals.tracker.nextTurn();
        Globals.updateNotification(context);
        Globals.listAdapter.notifyDataSetChanged();
    }

}

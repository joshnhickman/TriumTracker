package com.joshnhickman.triumtracker.control;

import android.view.View;
import android.widget.Button;

import com.joshnhickman.triumtracker.Globals;
import com.joshnhickman.triumtracker.R;


public class Combat {

    public static void start(Button actionButton, Button stopButton) {
        if (!Globals.combat) {
            Globals.combat = true;
            actionButton.setText(Globals.context.getString(R.string.next));
            stopButton.setVisibility(View.VISIBLE);
            Globals.tracker.nextTurn();
            NotificationUpdater.updateNotification(Globals.context,
                    Globals.tracker.getCurrentActor(),
                    Globals.tracker.getNextActors(1));
        }
    }

    public static void stop(Button actionButton, Button stopButton) {
        if (Globals.combat) {
            Globals.combat = false;
            actionButton.setText(Globals.context.getString(R.string.start));
            stopButton.setVisibility(View.INVISIBLE);
            NotificationUpdater.stopNotification(Globals.context);
            Globals.tracker.stop();
        }
    }

    public static void nextTurn() {
        if (Globals.combat) {
            Globals.tracker.nextTurn();
            NotificationUpdater.updateNotification(Globals.context,
                    Globals.tracker.getCurrentActor(),
                    Globals.tracker.getNextActors(1));
        }
    }

}

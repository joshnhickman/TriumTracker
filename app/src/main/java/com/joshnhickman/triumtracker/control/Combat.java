package com.joshnhickman.triumtracker.control;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joshnhickman.triumtracker.Globals;
import com.joshnhickman.triumtracker.R;


public class Combat {

    public static void restore() {

    }

    public static void start(View stopButton) {
        if (Globals.tracker.getNumberOfActors() == 0 ) {
            Toast.makeText(Globals.context, "add a character first", Toast.LENGTH_SHORT).show();
        } else if (!Globals.combat) {
            Globals.combat = true;
            stopButton.setVisibility(View.VISIBLE);
            Globals.tracker.nextTurn();
            NotificationUpdater.updateNotification(Globals.context,
                    Globals.tracker.getCurrentActor(),
                    Globals.tracker.getNextActors(1));
        }
    }

    public static void stop(View stopButton) {
        if (Globals.combat) {
            Globals.combat = false;
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

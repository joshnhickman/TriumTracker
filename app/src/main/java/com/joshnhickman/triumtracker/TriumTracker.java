package com.joshnhickman.triumtracker;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.joshnhickman.triumtracker.control.Combat;
import com.joshnhickman.triumtracker.control.NotificationUpdater;
import com.joshnhickman.triumtracker.domain.Actor;
import com.joshnhickman.triumtracker.domain.Disposition;
import com.joshnhickman.triumtracker.control.Tracker;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class TriumTracker extends AppCompatActivity {

    private static final String FILE_NAME = "trium_tracker_save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trium_tracker);

        loadState();

        Globals.context = getApplicationContext();

        // initializes the buttons
        final FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.floating_action);
        final Button stopButton = (Button) findViewById(R.id.stop);

        if (Globals.combat) {
            stopButton.setVisibility(View.VISIBLE);
            NotificationUpdater.updateNotification(getApplicationContext(),
                    Globals.tracker.getCurrentActor(),
                    Globals.tracker.getNextActors(1));
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Globals.combat) {
                    Combat.start(stopButton);
                } else {
                    Combat.nextTurn();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (Globals.combat) {
                Combat.stop(stopButton);
            }
            }
        });

        // initialize the view
        ListView listView = (ListView) findViewById(R.id.list_view);
        Globals.listAdapter = new ListAdapter(this, Globals.tracker);
        listView.setAdapter(Globals.listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trium_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new) {
            LayoutInflater inflater = LayoutInflater.from(this);
//            final View newCharacterView = inflater.inflate(R.layout.character_creator, null);
            final View newCharacterView = inflater.inflate(R.layout.character_creator,
                    (ViewGroup) findViewById(android.R.id.content), false);
            new AlertDialog.Builder(this)
                    .setTitle("Add new character")
                    .setView(newCharacterView)
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String characterName = ((EditText) newCharacterView.findViewById(R.id.character_name_edit)).getText().toString();
                            String playerName = ((EditText) newCharacterView.findViewById(R.id.player_name_edit)).getText().toString();
                            RadioGroup dispositionRadioGroup =
                                    (RadioGroup) newCharacterView.findViewById(R.id.radio_disposition);
                            RadioButton dispositionRadioButton =
                                    (RadioButton) newCharacterView.findViewById(dispositionRadioGroup.getCheckedRadioButtonId());
                            Disposition disposition =
                                    Disposition.valueOf(dispositionRadioButton.getText().toString().toUpperCase());
                            if (disposition != Disposition.PARTY && playerName.isEmpty())
                                playerName = "Game Master";

                            if (characterName.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "character name must be set", Toast.LENGTH_SHORT).show();
                            } else {
                                Globals.tracker.addActor(new Actor(characterName, playerName, disposition));
                                Globals.listAdapter.notifyDataSetChanged();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) { }
                    })
                    .show();
            return true;
        }
        if (id == R.id.action_discard) {
            if (!Globals.combat) {
                Globals.tracker.clearNonParty();
            } else {
                Toast.makeText(getApplicationContext(), "cannot delete during combat", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == R.id.action_discard_all) {
            if (!Globals.combat) {
                Globals.tracker.clear();
            } else {
                Toast.makeText(getApplicationContext(), "cannot delete during combat", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(Globals.NOTIFICATION_ID);
    }

    /**
     * Saves the current characters
     */
    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(FILE_NAME, Context.MODE_PRIVATE))) {
            oos.writeObject(Globals.tracker);
            oos.writeBoolean(Globals.combat);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "failed to save state", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Loads the last used characters
     */
    private void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(openFileInput(FILE_NAME))) {
            Globals.tracker = (Tracker) ois.readObject();
            Globals.combat = ois.readBoolean();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "failed to load saved state", Toast.LENGTH_SHORT).show();
        }
        if (Globals.tracker == null) {
            Globals.tracker = new Tracker();
        }
    }

}

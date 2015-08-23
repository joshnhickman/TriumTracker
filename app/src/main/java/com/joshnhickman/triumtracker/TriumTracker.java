package com.joshnhickman.triumtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Actor;
import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Tracker;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class TriumTracker extends Activity {

    private static final String FILE_NAME = "trium_tracker_save";

    private ListAdapter listAdapter;
    private Tracker tracker;
//    private int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trium_tracker);

        loadState();

//        final TextView roundView = (TextView) findViewById(R.id.round);
//        final TextView timeView = (TextView) findViewById(R.id.time);
        Button nextButton = (Button) findViewById(R.id.next);

        // initialize the next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker.nextActor();
//                roundView.setText("Round: " + currentRound);
//                timeView.setText("Time: " + (currentRound * 6) + "s");
//                listAdapter.notifyDataSetChanged();
                Globals.updateNotification(getApplicationContext());
            }
        });

        // initialize the view
        ListView listView = (ListView) findViewById(R.id.list_view);
        listAdapter = new ListAdapter(this, tracker);
        listView.setAdapter(listAdapter);
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
            final View newCharacterView = inflater.inflate(R.layout.character_creator, null);
            new AlertDialog.Builder(this)
                    .setTitle("Add new character")
                    .setView(newCharacterView)
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String characterName = ((EditText) newCharacterView.findViewById(R.id.character_name_edit)).getText().toString();
                            String playerName = ((EditText) newCharacterView.findViewById(R.id.player_name_edit)).getText().toString();
                            boolean ally = ((CheckBox) newCharacterView.findViewById(R.id.ally_check)).isChecked();
                            if (characterName.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Character name must be set", Toast.LENGTH_SHORT).show();
                            } else {
                                tracker.addActor(new Actor(characterName, playerName, ally));
                                listAdapter.notifyDataSetChanged();
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
            tracker.clear();
            listAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    /**
     * Saves the current characters
     */
    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(FILE_NAME, Context.MODE_PRIVATE))) {
            oos.writeObject(tracker.getActors());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "failed to save state", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Loads the last used characters
     */
    @SuppressWarnings("unchecked")
    private void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(openFileInput(FILE_NAME))) {
            tracker = new Tracker((List<Actor>) ois.readObject());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "failed to load saved state", Toast.LENGTH_SHORT).show();
        }
        if (tracker == null) {
            tracker = new Tracker();
        }
        Globals.tracker = tracker;
    }

}

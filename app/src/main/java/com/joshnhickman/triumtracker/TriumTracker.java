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
import android.widget.TextView;
import android.widget.Toast;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao.Actor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TriumTracker extends Activity {
    private static final String FILE_NAME = "trium_tracker_save";

    private ListAdapter listAdapter;
    private List<Actor> actors;
    private int currentRound, currentActor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trium_tracker);

        loadState();

        final TextView roundView = (TextView) findViewById(R.id.round);
        final TextView timeView = (TextView) findViewById(R.id.time);
        Button nextButton = (Button) findViewById(R.id.next);
        currentActor = -1;
        currentRound = 0;

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentActor++;
                if (currentActor > 0 && currentActor < actors.size()) {
                    if (currentActor - 1 >= 0) actors.get(currentActor).setCurrent(false);
                    actors.get(currentActor).setCurrent(true);
                } else {
                    currentActor = 0;
                    currentRound++;
                }
                roundView.setText("Round: " + currentRound);
                timeView.setText("Time: " + (currentRound * 6) + "s");
                listAdapter.notifyDataSetChanged();
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_view);
        listAdapter = new ListAdapter(this, actors);
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
                            if (characterName == null || characterName.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Character name must be set", Toast.LENGTH_SHORT).show();
                            } else {
                                actors.add(new Actor(characterName, playerName, ally));
                                Collections.sort(actors);
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
            actors.clear();
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

    private void saveState() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(actors);
        } catch (Exception e) {
        } finally {
            try {
                if (fos != null) fos.close();
                if (oos != null) oos.close();
            } catch (Exception e) { }
        }
    }

    private void loadState() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(FILE_NAME);
            ois = new ObjectInputStream(fis);
            actors = (List<Actor>) ois.readObject();
        } catch (Exception e) {
        } finally {
            try {
                if (fis != null) fis.close();
                if (ois != null) ois.close();
            } catch (Exception e) { }
        }
        if (actors == null) {
            actors = new ArrayList<Actor>();
        }
    }
}

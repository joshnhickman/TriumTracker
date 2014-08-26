package com.joshnhickman.triumtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao.PlayerCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TriumTracker extends Activity {
    private List<PlayerCharacter> characters;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trium_tracker);

        characters = new ArrayList<PlayerCharacter>();

        ListView listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ListAdapter(this, characters);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                alert.setTitle(characters.get(position).getCharacterName());

                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View initiativeView = inflater.inflate(R.layout.initiative_setter, parent, false);
                alert.setView(initiativeView);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String initiative = ((EditText) initiativeView.findViewById(R.id.new_initiative)).getText().toString();
                        characters.get(position).setInitiativeScore(Integer.valueOf(initiative));
                        Collections.sort(characters);
                        listAdapter.notifyDataSetChanged();
                    }
                });

                alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancelled
                    }
                });

                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        characters.remove(position);
                        listAdapter.notifyDataSetChanged();
                    }
                });

                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trium_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Add new character");

            LayoutInflater inflater = LayoutInflater.from(this);
            final View newCharacterView = inflater.inflate(R.layout.character_creator, null);
            alert.setView(newCharacterView);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String characterName = ((EditText) newCharacterView.findViewById(R.id.character_name_edit)).getText().toString();
                    String playerName = ((EditText) newCharacterView.findViewById(R.id.player_name_edit)).getText().toString();
                    boolean ally = ((CheckBox) newCharacterView.findViewById(R.id.ally_check)).isChecked();
                    if (characterName == null || characterName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Character name must be set", Toast.LENGTH_SHORT).show();
                    } else {
                        characters.add(new PlayerCharacter(characterName, playerName, ally));
                        Collections.sort(characters);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Cancelled
                }
            });

            alert.show();
            return true;
        }
        if (id == R.id.action_discard) {
            characters.clear();
            listAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

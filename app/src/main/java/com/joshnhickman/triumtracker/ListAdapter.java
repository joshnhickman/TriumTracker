package com.joshnhickman.triumtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao.Actor;

import java.util.Collections;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Actor> actors;

    public ListAdapter(Context context, List<Actor> actors) {
        this.context = context;
        this.actors = actors;
    }

    @Override
    public int getCount() {
        return actors.size();
    }

    @Override
    public Object getItem(int position) {
        return actors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        ImageView plus = (ImageView) rowView.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actor thisActor = actors.get(position);
                if (actors.size() >= 2 && position > 0) {
                    Actor nextActor = actors.get(position - 1);
                    if (nextActor.getInit() - thisActor.getInit() >= 1) {
                        thisActor.increaseInit(1);
                        if (nextActor.getInit() - thisActor.getInit() == 0) {
                            thisActor.increaseInitMod(-1);
                        }
                    } else if (nextActor.getInitMod() - thisActor.getInitMod() >= 1){
                        thisActor.setInitMod(nextActor.getInitMod() + 1);
                    } else {
                        thisActor.increaseInit(1);
                    }
                } else {
                    thisActor.increaseInit(1);
                }
                Collections.sort(actors);
                notifyDataSetChanged();
            }
        });
        ImageView minus = (ImageView) rowView.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actor thisActor = actors.get(position);
                if (actors.size() >= 2 && position < actors.size() - 1) {
                    Actor prevActor = actors.get(position + 1);
                    if (prevActor.getInit() - thisActor.getInit() <= -1) {
                        thisActor.increaseInit(-1);
                        if (prevActor.getInit() - thisActor.getInit() == 0) {
                            thisActor.setInitMod(prevActor.getInitMod() + 1);
                        }
                    } else if (prevActor.getInitMod() - thisActor.getInitMod() <= -1){
                        thisActor.setInitMod(prevActor.getInitMod() - 1);
                    } else {
                        thisActor.increaseInit(-1);
                    }
                } else {
                    thisActor.increaseInit(-1);
                }
                Collections.sort(actors);
                notifyDataSetChanged();
            }
        });

        TextView characterName = (TextView) rowView.findViewById(R.id.actor_name);
        characterName.setText(actors.get(position).getName());
        TextView playerName = (TextView) rowView.findViewById(R.id.player_name);
        playerName.setText(actors.get(position).getPlayerName());
        TextView initiativeScore = (TextView) rowView.findViewById(R.id.init);
        initiativeScore.setText(actors.get(position).getInitAsString());
        if (actors.get(position).isCurrent()) {
            playerName.setTextColor(context.getResources().getColor(R.color.yellow));
        } else {
            playerName.setTextColor(context.getResources().getColor(R.color.black));
        }
        if (actors.get(position).isAlly()) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        initiativeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View initiativeView = inflater.inflate(R.layout.initiative_setter, parent, false);
                new AlertDialog.Builder(view.getContext())
                        .setTitle(actors.get(position).getName())
                        .setView(input)
                        .setView(initiativeView)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String initString = ((EditText) initiativeView.findViewById(R.id.new_init)).getText().toString();
                                if (initString.isEmpty()) initString = actors.get(position).getInitAsString();
                                String initModString = ((EditText) initiativeView.findViewById(R.id.new_init_mod)).getText().toString();
                                if (initModString.isEmpty()) initModString = actors.get(position).getInitModAsString();
                                try {
                                    actors.get(position).setInit(Integer.parseInt(initString));
                                    actors.get(position).setInitMod(Integer.parseInt(initModString));
                                    Collections.sort(actors);
                                    notifyDataSetChanged();
                                } catch (NumberFormatException e) {
                                    Toast.makeText(context, "Initiative must be a valid number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancelled
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                actors.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

        return rowView;
    }
}

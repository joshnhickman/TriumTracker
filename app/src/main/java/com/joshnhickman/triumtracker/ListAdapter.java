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

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Actor;
import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain.Tracker;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private Tracker tracker;

    public ListAdapter(Context context, Tracker tracker) {
        this.context = context;
        this.tracker = tracker;
    }

    @Override
    public int getCount() {
        return tracker.getNumberOfActors();
    }

    @Override
    public Object getItem(int position) {
        return tracker.getActor(position);
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
                Actor thisActor = tracker.getActor(position);
                if (tracker.getNumberOfActors() >= 2 && position > 0) {
                    Actor nextActor = tracker.getActor(position - 1);
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
                tracker.sort();
                notifyDataSetChanged();
            }
        });
        ImageView minus = (ImageView) rowView.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actor thisActor = tracker.getActor(position);
                if (tracker.getNumberOfActors() >= 2 && position < tracker.getNumberOfActors() - 1) {
                    Actor prevActor = tracker.getActor(position + 1);
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
                tracker.sort();
                notifyDataSetChanged();
            }
        });

        TextView characterName = (TextView) rowView.findViewById(R.id.actor_name);
        characterName.setText(tracker.getActor(position).getName());
        TextView playerName = (TextView) rowView.findViewById(R.id.player_name);
        playerName.setText(tracker.getActor(position).getPlayerName());
        TextView initiativeScore = (TextView) rowView.findViewById(R.id.init);
        initiativeScore.setText(tracker.getActor(position).getInitAsString());
        if (tracker.getActor(position).isAlly()) {
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
                        .setTitle(tracker.getActor(position).getName())
                        .setView(input)
                        .setView(initiativeView)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String initString = ((EditText) initiativeView.findViewById(R.id.new_init)).getText().toString();
                                if (initString.isEmpty()) initString = tracker.getActor(position).getInitAsString();
                                String initModString = ((EditText) initiativeView.findViewById(R.id.new_init_mod)).getText().toString();
                                if (initModString.isEmpty()) initModString = tracker.getActor(position).getInitModAsString();
                                try {
                                    tracker.getActor(position).setInit(Integer.parseInt(initString));
                                    tracker.getActor(position).setInitMod(Integer.parseInt(initModString));
                                    tracker.sort();
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
                                tracker.removeActor(position);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

        return rowView;
    }
}

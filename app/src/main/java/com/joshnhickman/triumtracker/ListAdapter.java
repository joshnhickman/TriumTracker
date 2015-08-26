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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actorView = inflater.inflate(R.layout.actor, parent, false);

        final Actor thisActor = tracker.getActor(position);

        ImageView plus = (ImageView) actorView.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    Actor prevActor = tracker.getActor(position - 1);
                    if (thisActor.getInit() == prevActor.getInit()) {
                        thisActor.setInitMod(prevActor.getInitMod() + 1);
                    } else {
                        thisActor.increaseInit(1);
                        if (thisActor.getInit() == prevActor.getInit() && prevActor.getInitMod() == 0) {
                            thisActor.increaseInitMod(-1);
                        }
                    }
                } else {
                    thisActor.increaseInit(1);
                }
                tracker.sort();
            }
        });
        ImageView minus = (ImageView) actorView.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < tracker.getNumberOfActors() - 1) {
                    Actor nextActor = tracker.getActor(position + 1);
                    if (thisActor.getInit() == nextActor.getInit()) {
                        thisActor.setInitMod(nextActor.getInitMod() - 1);
                    } else {
                        thisActor.increaseInit(-1);
                        if (thisActor.getInit() == nextActor.getInit()) {
                            thisActor.setInitMod(nextActor.getInitMod() + 1);
                        }
                    }
                } else {
                    thisActor.increaseInit(-1);
                }
                tracker.sort();
            }
        });

        // set TextViews
        ((TextView) actorView.findViewById(R.id.actor_name)).setText(thisActor.getName());
        ((TextView) actorView.findViewById(R.id.player_name)).setText(thisActor.getPlayerName());
        ((TextView) actorView.findViewById(R.id.init)).setText(thisActor.getInitAsString());

        // set disposition indicator
        actorView.findViewById(R.id.disposition_indicator)
                .setBackgroundColor(context.getResources().getColor(tracker.getActor(position).getDisposition().getColorId()));
        if (tracker.isCurrentActor(position)) {
            actorView.setBackgroundColor(context.getResources().getColor(tracker.getActor(position).getDisposition().getColorId()));
        }

        // set initiative score setter
        TextView initiativeScore = (TextView) actorView.findViewById(R.id.init);
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

        return actorView;
    }
}

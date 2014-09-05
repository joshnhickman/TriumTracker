package com.joshnhickman.triumtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao.Actor;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView characterName = (TextView) rowView.findViewById(R.id.actor_name);
        characterName.setText(actors.get(position).getName());
        TextView playerName = (TextView) rowView.findViewById(R.id.player_name);
        playerName.setText(actors.get(position).getPlayerName());
        TextView initiativeScore = (TextView) rowView.findViewById(R.id.init);
        initiativeScore.setText(actors.get(position).getInitAsString());
        if (actors.get(position).isAlly()) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        return rowView;
    }
}

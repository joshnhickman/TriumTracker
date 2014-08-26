package com.joshnhickman.triumtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao.PlayerCharacter;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<PlayerCharacter> characters;

    public ListAdapter(Context context, List<PlayerCharacter> characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView characterName = (TextView) rowView.findViewById(R.id.character);
        characterName.setText(characters.get(position).getCharacterName());
        TextView playerName = (TextView) rowView.findViewById(R.id.player);
        playerName.setText(characters.get(position).getPlayerName());
        TextView initiativeScore = (TextView) rowView.findViewById(R.id.score);
        initiativeScore.setText(characters.get(position).getInitiativeScoreAsString());
        if (characters.get(position).getAlly()) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        return rowView;
    }
}

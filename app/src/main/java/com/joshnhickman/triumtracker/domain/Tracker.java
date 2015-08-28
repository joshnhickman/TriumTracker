package com.joshnhickman.triumtracker.domain;

import com.joshnhickman.triumtracker.Globals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tracker implements Serializable {

    private List<Actor> actors;
    private int currentTurn;

    public Tracker() {
        currentTurn = -1;
        actors = new ArrayList<>();
    }

    public Tracker(List<Actor> actors) {
        this();
        this.actors = actors;
    }

    /**
     * @param actor the Actor to add
     */
    public void addActor(Actor actor) {
        actors.add(actor);
        sort();
    }

    /**
     * moves to the next Actor
     */
    public void nextTurn() {
        if (!actors.isEmpty()) {
            currentTurn++;
            if (!isInBounds(currentTurn)) {
                currentTurn = 0;
            }
        } else {
            currentTurn = -1;
        }
        Globals.listAdapter.notifyDataSetChanged();
    }

    public void resetTurn() {
        currentTurn = -1;
        nextTurn();
        Globals.listAdapter.notifyDataSetChanged();
    }

    private boolean isInBounds(int index) {
        return !(index < 0 || index > actors.size() - 1);
    }

    /**
     * @return the current Actor
     */
    public Actor getCurrentActor() {
        if (!actors.isEmpty() && currentTurn >= 0) {
            return actors.get(currentTurn);
        }
        return null;
    }

    public Actor[] getNextActors(int number) {
        Actor[] returnedActors = new Actor[number];
        int tempTurn = currentTurn;
        for (int i = 0; i < number; i++) {
            tempTurn++;
            if (tempTurn >= actors.size()) {
                tempTurn = 0;
            }
            returnedActors[i] = actors.get(tempTurn);
        }
        return returnedActors;
    }

    public boolean isCurrentActor(int index) {
        return currentTurn == index;
    }

    /**
     * @param index the index of the desired Actor
     * @return the Actor at the given index
     */
    public Actor getActor(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be greater than 0");
        }
        if (isInBounds(index)) {
            return actors.get(index);
        } else {
            return null;
        }
    }

    /**
     * @return the List of Actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * @return the number of Actors being tracked
     */
    public int getNumberOfActors() {
        return actors.size();
    }

    /**
     * sorts the Actors
     */
    public void sort() {
        Collections.sort(actors);
        Globals.listAdapter.notifyDataSetChanged();
    }

    /**
     * Removes the Actor at the given index
     * @param index the index of the Actor to remove
     */
    public void removeActor(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be greater than 0");
        }
        if (isInBounds(index)) {
            actors.remove(index);
        }
        Globals.listAdapter.notifyDataSetChanged();
    }

    /**
     * Clears the List of Actors
     */
    public void clear() {
        actors.clear();
        currentTurn = -1;
        Globals.listAdapter.notifyDataSetChanged();
    }

}

package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tracker implements Serializable {

    private List<Actor> actors;
    private int currentTurn;
    private int nextTurn;

    public Tracker() {
        currentTurn = -1;
        nextTurn = -1;
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
            if (actors.size() > 1) {
                nextTurn = currentTurn + 1;
                if (!isInBounds(nextTurn)) {
                    nextTurn = 0;
                }
            } else {
                nextTurn = -1;
            }
        } else {
            currentTurn = -1;
            nextTurn = -1;
        }
    }

    public void resetTurn() {
        currentTurn = -1;
        nextTurn = -1;
        nextTurn();
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

    public boolean isCurrentActor(int index) {
        return currentTurn == index;
    }

    /**
     * @return the next Actor
     */
    public Actor getNextTurn() {
        if (actors.size() > 1) {
            return actors.get(nextTurn);
        }
        return null;
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
    }

    /**
     * Clears the List of Actors
     */
    public void clear() {
        actors.clear();
        currentTurn = -1;
        nextTurn = -1;
    }

}

package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tracker implements Serializable {

    private List<Actor> actors;
    private int currentActor;
    private int nextActor;

    public Tracker() {
        currentActor = -1;
        nextActor = -1;
        actors = new ArrayList<>();
    }

    public Tracker(List<Actor> actors) {
        currentActor = -1;
        nextActor = -1;
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
    public void nextActor() {
        currentActor++;
        if (!isInBounds(currentActor)) {
            currentActor = 0;
        }
        if (actors.size() > 1) {
            nextActor = currentActor + 1;
            if (!isInBounds(nextActor)) {
                nextActor = 0;
            }
        } else {
            nextActor = -1;
        }
    }

    private boolean isInBounds(int index) {
        return !(index < 0 || index > actors.size() - 1);
    }

    /**
     * @return the current Actor
     */
    public Actor getCurrentActor() {
        if (!actors.isEmpty()) {
            return actors.get(currentActor);
        }
        return null;
    }

    /**
     * @return the next Actor
     */
    public Actor getNextActor() {
        if (actors.size() > 1) {
            return actors.get(nextActor);
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

    public void clear() {
        actors.clear();
        currentActor = -1;
        nextActor = -1;
    }

}

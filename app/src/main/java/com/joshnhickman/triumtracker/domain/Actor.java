package com.joshnhickman.triumtracker.domain;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Actor implements Comparable<Actor>, Serializable {

    private String name;
    private String playerName;
    private Disposition disposition;
    private Initiative init;
    private int initMod;

    public Actor(String name, String playerName, Disposition disposition) {
        this(name, playerName, disposition, new Initiative(0), 0);
    }

    public Actor(String name, String playerName, Disposition disposition, Initiative init, int initMod) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (disposition == null) {
            throw new IllegalArgumentException("disposition cannot be empty");
        }
        this.name = name;
        this.playerName = playerName;
        this.disposition = disposition;
        this.init = init;
        this.initMod = initMod;
    }

    public String getName() {
        return name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setInit(Initiative init) {
        if (this.init.getScore() != init.getScore()) {
            initMod = 0;
        }
        this.init = init;
    }

    public Initiative getInit() {
        return init;
    }

    public int getInitScore() {
        return init.getScore();
    }

    public String getInitAsString() {
        return init.toString();
    }

    public void setInitMod(int initMod) {
        this.initMod = initMod;
    }

    public int getInitMod() {
        return initMod;
    }

    public Disposition getDisposition() {
        return disposition;
    }

    @Override
    public int compareTo(@NonNull Actor another) {
        int initCompare = init.compareTo(another.getInit());
        if (initCompare == 0) {
            return another.getInitMod() - initMod;
        }
        return initCompare;
    }

    @Override
    public String toString() {
        String s = getName();
        if (getPlayerName() != null && !getPlayerName().isEmpty()) {
            s += " (" + getPlayerName() + ")";
        }
        return s;
    }
}

package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain;

import java.io.Serializable;

public class Actor implements Comparable<Actor>, Serializable {

    private String name;
    private String playerName;
    private int init;
    private int initMod;
    private boolean ally;

    public Actor(String name, String playerName, boolean ally) {
        this(name, playerName, ally, 0, 0);
    }

    public Actor(String name, String playerName, boolean ally,
                 int init, int initMod) {
        this.name = name;
        this.playerName = playerName;
        this.ally = ally;
        this.init = init;
        this.initMod = initMod;
    }

    public String getName() {
        return name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void increaseInit(int inc) {
        init += inc;
        initMod = 0;
    }

    public void setInit(int init) {
        if (this.init != init) {
            initMod = 0;
        }
        this.init = init;
    }

    public int getInit() {
        return init;
    }

    public String getInitAsString() {
        return "" + init;
    }

    public void increaseInitMod(int inc) {
        initMod += inc;
    }

    public void setInitMod(int initMod) {
        this.initMod = initMod;
    }

    public int getInitMod() {
        return initMod;
    }

    public String getInitModAsString() {
        return "" + initMod;
    }

    public boolean isAlly() {
        return ally;
    }

    @Override
    public int compareTo(Actor another) {
        int initDiff = another.getInit() - init;
        if (initDiff != 0) {
            return initDiff;
        }
        return another.getInitMod() - initMod;
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

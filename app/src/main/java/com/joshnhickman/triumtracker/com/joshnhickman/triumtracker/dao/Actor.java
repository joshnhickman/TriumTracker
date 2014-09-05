package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao;

import java.io.Serializable;

/**
 * Created by Josh on 8/25/2014.
 */
public class Actor implements Comparable<Actor>, Serializable {

    private String name, playerName;
    private int init, initMod;
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

    public boolean isAlly() {
        return ally;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getInit() {
        return init;
    }

    public String getInitAsString() {
        return "" + init;
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

    @Override
    public int compareTo(Actor another) {
        int initDiff = another.getInit() - init;
        if (initDiff != 0) {
            return initDiff;
        }
        return another.getInitMod() - initMod;
    }
}

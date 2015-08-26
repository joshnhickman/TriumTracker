package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain;

public class InitiativeScore {

    public int roll;

    public InitiativeScore(int roll) {
        if (roll < 1 || roll > 20) {
            throw new IllegalArgumentException("roll must be between 1 and 20");
        }
        this.roll = roll;
    }

    public int getRoll() {
        return roll;
    }

    public boolean isCritHit() {
        return roll == 20;
    }

    public boolean isCritMiss() {
        return roll == 1;
    }

}

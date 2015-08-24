package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.domain;

import com.joshnhickman.triumtracker.R;

public enum Disposition {

    PARTY(R.color.party),
    ALLY(R.color.ally),
    NEUTRAL(R.color.neutral),
    ENEMY(R.color.enemy),
    DANGER(R.color.danger);

    private int colorId;

    Disposition(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }

}

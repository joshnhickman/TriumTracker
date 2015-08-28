package com.joshnhickman.triumtracker.domain;

import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.joshnhickman.triumtracker.R;

import java.io.Serializable;

public class Initiative implements Comparable<Initiative>, Serializable {

    private int score;
    private boolean critHit;
    private boolean critMiss;

    public Initiative(int score) {
        this(score, RollType.NORMAL);
    }

    public Initiative(int score, RollType rollType) {
        this.score = score;
        critHit = rollType == RollType.CRIT_HIT;
        critMiss = rollType == RollType.CRIT_MISS;
    }

    public Initiative(Initiative initiative) {
        score = initiative.getScore();
        critHit = initiative.isCritHit();
        critMiss = initiative.isCritMiss();
    }

    public Initiative(View view) {
        score = Integer.parseInt(((EditText) view.findViewById(R.id.new_init)).getText().toString());
        int checkRadioButtonId = ((RadioGroup) view.findViewById(R.id.radio_crit)).getCheckedRadioButtonId();
        if (checkRadioButtonId != -1) {
            String radioText = ((RadioButton) view.findViewById(checkRadioButtonId)).getText().toString();
            critHit = "Critical Hit".equals(radioText);
            critMiss = "Critical Miss".equals(radioText);
        }
    }

    public int getScore() {
        return score;
    }

    public boolean isCritHit() {
        return critHit;
    }

    public boolean isCritMiss() {
        return critMiss;
    }

    @Override
    public int compareTo(@NonNull Initiative another) {
        if (critHit) {
            if (another.isCritHit()) {
                return 0;
            } else {
                return -1;
            }
        } else if (another.isCritHit()) {
            return 1;
        } else if (critMiss) {
            if (another.isCritMiss()) {
                return 0;
            } else {
                return 1;
            }
        } else if (another.isCritMiss()) {
            return -1;
        }
        return another.getScore() - score;
    }

    @Override
    public String toString() {
        if (critHit) {
            return "" + Html.fromHtml("<sup>+</sup>") + score;
        } else if (critMiss) {
            return "" + Html.fromHtml("<sub>-</sub>") + score;
        }
        return "" + score;
    }
}

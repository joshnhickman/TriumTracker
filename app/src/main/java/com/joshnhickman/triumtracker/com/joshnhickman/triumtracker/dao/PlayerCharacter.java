package com.joshnhickman.triumtracker.com.joshnhickman.triumtracker.dao;

/**
 * Created by Josh on 8/25/2014.
 */
public class PlayerCharacter implements Comparable<PlayerCharacter> {

    private String characterName, playerName;
    private int initiativeScore;
    private boolean ally;

    public PlayerCharacter(String characterName, String playerName, boolean ally) {
        this.characterName = characterName;
        this.playerName = playerName;
        this.ally = ally;
        initiativeScore = 0;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean getAlly() {
        return ally;
    }

    public void setInitiativeScore(int initiativeScore) {
        this.initiativeScore = initiativeScore;
    }

    public int getInitiativeScore() {
        return initiativeScore;
    }

    public String getInitiativeScoreAsString() {
        return "" + initiativeScore;
    }

    @Override
    public int compareTo(PlayerCharacter another) {
        return another.getInitiativeScore() - initiativeScore;
    }
}

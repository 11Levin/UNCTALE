package edu.unc.comp301.model.piece;

import java.util.Set;

public class GameStats extends PlayerStats {
    private final int level;
    private final int score;
    private final int highScore;
    private final int lastScore;
    private final boolean winGame;

    public GameStats(int level, int score, int highScore, int lastScore, int attack, int health, Set<String> buffs, boolean winGame) {
        super(attack, health, buffs);
        this.level = level;
        this.score = score;
        this.highScore = highScore;
        this.lastScore = lastScore;
        this.winGame = winGame;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLastScore() {
        return lastScore;
    }

    public boolean getWinGame() {return winGame;}
}
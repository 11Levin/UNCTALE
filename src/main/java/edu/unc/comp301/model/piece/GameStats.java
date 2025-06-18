package edu.unc.comp301.model.piece;

import java.util.*;

public class GameStats extends PlayerStats{
    private final int level;
    private final int score;
    private final int highScore;

    public GameStats(int level, int score, int highScore, int attack, int health, Set<String> buffs) {
        super(attack, health, buffs);
        this.level = level;
        this.score = score;
        this.highScore = highScore;
    }

    public int getLevel() {return level;}

    public int getScore() {return score;}

    public int getHighScore() {return highScore;}
}
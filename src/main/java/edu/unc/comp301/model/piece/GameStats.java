package edu.unc.comp301.model.piece;

public class GameStats extends PlayerStats{
    private final int level;
    private final int score;

    public GameStats(int level, int score, int attack, int health) {
        super(attack, health);
        this.level = level;
        this.score = score;
    }

    public int getLevel() {return level;}

    public int getScore() {return score;}
}
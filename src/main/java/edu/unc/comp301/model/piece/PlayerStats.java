package edu.unc.comp301.model.piece;

public class PlayerStats {
    private final int attack;
    private final int health;

    public PlayerStats(int attack, int health) {
        this.attack = attack;
        this.health = health;
    }

    public int getAttack() {return Math.max(attack, 5);}

    public int getHealth() {return health;}
}

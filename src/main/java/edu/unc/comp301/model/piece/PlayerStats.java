package edu.unc.comp301.model.piece;

import java.util.Set;

public class PlayerStats {
    private final int attack;
    private final int health;
    private final Set<String> buffs;

    public PlayerStats(int attack, int health, Set<String> buffs) {
        this.attack = attack;
        this.health = health;
        this.buffs = buffs;
    }

    public int getAttack() {
        return Math.max(attack, 5);
    }

    public int getHealth() {
        return health;
    }

    public Set<String> getBuffs() {
        return buffs;
    }
}

package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Enemy;

public class BulletEnemy extends Character implements Enemy {
    public BulletEnemy(Posn posn, int health) {
        super("bullet", "UNCBullet_Enemy.png", posn, health);
    }
}

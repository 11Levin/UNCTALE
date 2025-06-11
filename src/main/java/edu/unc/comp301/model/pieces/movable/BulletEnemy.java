package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.requirements.Enemy;

public class BulletEnemy extends Character implements Enemy {
    public BulletEnemy(Posn posn, int health) {
        super("bullet", "UNCBullet_Enemy.png", posn, health);
    }
}

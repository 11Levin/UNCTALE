package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Bullet extends Enemy {
    public Bullet(Posn posn, int health, int attack) {
        super("bullet", "movable/UNCBullet_Enemy.png", posn, health, attack);
    }
}

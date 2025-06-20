package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Bullet extends Enemy {
    private final int[] direction;

    public Bullet(Posn posn, int attack, int[] direction, int speed) {
        super("bullet", "movable/UNCBullet_Enemy.png", posn, 1, attack, speed);
        this.direction = direction;
    }

    public int[] getDirection() {
        return direction;
    }
}

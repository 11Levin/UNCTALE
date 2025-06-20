package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.movable.*;

public class EnemyFactory {
    private EnemyFactory() {
    }

    public static Enemy createEnemy(String name, Posn posn) {
        name = name.toLowerCase();
        if (name.equals("student")) {
            return new Student(posn);
        }
        if (name.equals("professor")) {
            return new Professor(posn);
        }
        if (name.equals("ramses")) {
            return new Ramses(posn);
        }
        return null;
    }

    public static Enemy createBullet(Posn posn, int attack, int[] direction, int speed) {
        return new Bullet(posn, attack, direction, speed);
    }

}

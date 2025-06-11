package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.requirements.Enemy;

public class StudentEnemy extends Character implements Enemy {
    public StudentEnemy(Posn posn, int health) {
        super("student", "UNCStudent_Enemy.png", posn, health);
    }
}

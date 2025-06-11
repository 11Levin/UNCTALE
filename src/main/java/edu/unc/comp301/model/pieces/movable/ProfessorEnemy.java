
package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.requirements.Enemy;

public class ProfessorEnemy extends Character implements Enemy {
    public ProfessorEnemy(Posn posn, int health) {
        super("professor", "UNCProfessor_Enemy.png", posn, health);
    }
}

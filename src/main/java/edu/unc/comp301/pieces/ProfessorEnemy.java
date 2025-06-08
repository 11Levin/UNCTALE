
package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Enemy;

public class ProfessorEnemy extends Character implements Enemy {
    public ProfessorEnemy(Posn posn, int health) {
        super("professor", "UNCProfessor_Enemy.png", posn, health);
    }
}

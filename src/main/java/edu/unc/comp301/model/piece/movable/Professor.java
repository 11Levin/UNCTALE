
package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Professor extends Enemy {
    public Professor(Posn posn, int health) {
        super("professor", "movable/UNCProfessor_Enemy.png", posn, health, 20);
    }
}

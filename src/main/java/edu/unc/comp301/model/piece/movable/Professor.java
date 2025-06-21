package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Professor extends Enemy {
  public Professor(Posn posn) {
    super("professor", "movable/UNCProfessor_Enemy.png", posn, 120, 20, 2);
  }
}

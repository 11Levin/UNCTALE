package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Student extends Enemy {
  public Student(Posn posn) {
    super("student", "movable/UNCStudent_Enemy.png", posn, 100, 20, 1);
  }
}

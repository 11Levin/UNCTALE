package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Student extends Enemy {
    public Student(Posn posn, int health) {
        super("student", "movable/UNCStudent_Enemy.png", posn, health, 20);
    }
}

package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;

public class Ramses extends Enemy{
    public Ramses(Posn posn) {
        super("ramses", "movable/UNCRamses_Enemy.png", posn, 500, 20,4);
    }
}

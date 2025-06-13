package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;

public class Obstacle extends ImmovablePiece {
    public Obstacle(Posn posn) {
        super("obstacle", "immovable/UNCRam_Obstacle.png", posn);
    }
}

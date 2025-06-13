package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;

public class Treasure extends ImmovablePiece {
    public Treasure(Posn posn) {
        super("treasure", "immovable/UNCMoney_Treasure.png", posn);
    }
}

package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Treasure extends ImmovablePiece {
    public Treasure(Posn posn) {
        super("treasure", "immovable/UNCMoney_Treasure.png", posn, new CollisionEvent(10, 0, 0, CollisionEvent.Result.CONTINUE));
    }
}

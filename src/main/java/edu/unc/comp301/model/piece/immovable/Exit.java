package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Exit extends ImmovablePiece {
    public Exit(Posn posn) {
        super("exit", "immovable/UNCLogo_Exit.png", posn, new CollisionEvent(20, 0, 0, CollisionEvent.Result.NEXT_LEVEL));
    }
}

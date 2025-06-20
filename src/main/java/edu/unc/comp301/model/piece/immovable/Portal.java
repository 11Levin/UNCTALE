package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Portal extends ImmovablePiece {
    private Portal other;

    public Portal(Posn posn) {
        super("portal", "immovable/UNCPortal.png", posn, new CollisionEvent(0, 0, 0, CollisionEvent.Result.TELEPORT));
    }

    public void setOther(Portal other) {
        this.other = other;
    }

    public Portal getOther() {
        return other;
    }
}

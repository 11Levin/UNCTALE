package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Pencil extends Buff {
    public Pencil(Posn posn) {
        super("pencil", "buff/UNCPencil_Buff.png", posn, new CollisionEvent(0, 5, 0, CollisionEvent.Result.CONTINUE));
    }
}
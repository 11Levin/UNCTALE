package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Homework extends Buff {
    public Homework(Posn posn) {
        super("homework", "buff/UNCHomework_Debuff.jpg", posn, new CollisionEvent(0, -5, -10, CollisionEvent.Result.PICK_ITEM));
    }
}
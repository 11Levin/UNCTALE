package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;

public class Pencil extends Buff {
    public Pencil(Posn posn) {
        super("pencil", "buff/UNCPencil_Buff.png", posn, 5, 0);
    }
}
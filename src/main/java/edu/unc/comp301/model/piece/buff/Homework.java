package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;

public class Homework extends Buff {
    public Homework(Posn posn){
        super("homework", "buff/UNCHomework_Debuff.jpg", posn, 0, -5);
    }
}
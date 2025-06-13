package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;

public class Apple extends Buff {
    public Apple(Posn posn) {
        super("apple", "buff/UNCApple_Buff.png", posn, 0, 5);
    }
}
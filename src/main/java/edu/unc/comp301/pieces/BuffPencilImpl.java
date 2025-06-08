package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Buff;

public class BuffPencilImpl extends APiece implements Buff {
    public BuffPencilImpl(Posn posn){
        super("pencil", "UNCPencil_Buff.png", posn);
    }

    public void increaseAttack(PlayerImpl player) {
        //player.setAttack(player.getAttack() + 5); //increase attack by 5
    }
}
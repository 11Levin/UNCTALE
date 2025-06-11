package edu.unc.comp301.model.pieces.buffs;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.movable.PlayerImpl;
import edu.unc.comp301.model.pieces.requirements.Buff;

public class BuffPencilImpl extends APiece implements Buff {
    public BuffPencilImpl(Posn posn){
        super("pencil", "UNCPencil_Buff.png", posn);
    }

    public void increaseAttack(PlayerImpl player) {
        //player.setAttack(player.getAttack() + 5); //increase attack by 5
    }
}
package edu.unc.comp301.model.pieces.buffs;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.movable.PlayerImpl;
import edu.unc.comp301.model.pieces.requirements.Buff;

public class BuffAppleImpl extends APiece implements Buff {
    public BuffAppleImpl(Posn posn){
        super("apple", "UNCApple_Buff.png", posn);
    }

    public void increaseHealth(PlayerImpl player) {
        player.setHealth(player.getHealth() + 5); //increase health by 5
    }

}
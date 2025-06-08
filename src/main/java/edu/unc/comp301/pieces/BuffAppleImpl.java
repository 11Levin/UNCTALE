package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Buff;

public class BuffAppleImpl extends APiece implements Buff {
    public BuffAppleImpl(Posn posn){
        super("apple", "UNCApple_Buff.png", posn);
    }

    public void increaseHealth(PlayerImpl player) {
        player.setHealth(player.getHealth() + 5); //increase health by 5
    }

}

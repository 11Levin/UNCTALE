package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Buff;

public class DebuffHomeworkImpl extends APiece implements Buff {
    public DebuffHomeworkImpl(Posn posn){
        super("homework", "UNCHomework_Debuff.jpg", posn);
    }

    public void decreaseStats(PlayerImpl player) {
        player.setHealth(player.getHealth() - 5); //decrease health by 5
    }

}

package edu.unc.comp301.model.pieces.buffs;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.movable.PlayerImpl;
import edu.unc.comp301.model.pieces.requirements.Buff;

public class DebuffHomeworkImpl extends APiece implements Buff {
    public DebuffHomeworkImpl(Posn posn){
        super("homework", "UNCHomework_Debuff.jpg", posn);
    }

    public void decreaseStats(PlayerImpl player) {
        player.setHealth(player.getHealth() - 5); //decrease health by 5
    }

}
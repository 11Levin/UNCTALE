package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Buff;

public class BuffImpl extends APiece implements Buff {
  public BuffImpl(String name, Posn posn){
    super("pencil", "UNCPencil_Buff.png", posn);
  }

  public void increaseHealth(PlayerImpl player) {
    player.setHealth(player.getHealth() + 5); //increase health by 5
  }

  public void increaseAttack(PlayerImpl player) {
    player.setAttack(player.getAttack() + 5); //increase attack by 5
  }
}

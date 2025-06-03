package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Player;

public class PlayerImpl extends Character implements Player {
  private int attack = 20;

  /**
   * @param posn         position (row, col) on board
   */
  public PlayerImpl(Posn posn, int health) {
    super("player", "resources/UNCPlayer.png", posn, health);
  }

  public int getAttack(){
    return this.attack;
  }

  public void setAttack(int attack){
    this.attack = attack;
  }

}

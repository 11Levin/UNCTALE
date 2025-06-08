package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Player;

public class PlayerImpl extends Character implements Player {
  private int attack = 20;
  private boolean next_level = false;

  /**
   * @param posn         position (row, col) on board
   */
  public PlayerImpl(Posn posn, int health) {
    super("player", "UNCPlayer.png", posn, health);
  }

  //prevents player from moving in between levels
  public boolean getNext_Level(){
    return this.next_level;
  }
  public void toggleNext_level(){
    this.next_level = !this.next_level;
  }

}
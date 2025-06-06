package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Player;

public class PlayerImpl extends Character implements Player {

  /**
   * @param posn         position (row, col) on board
   */
  public PlayerImpl(Posn posn, int health) {
    super("player", "UNCPlayer.png", posn, health);
  }

}

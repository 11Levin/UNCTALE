package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Exit;

public class ExitImpl extends APiece implements Exit {

  public ExitImpl(Posn posn) {
    super("treasure", "resources/UNCMoney_Treasure.png", posn);
  }
}

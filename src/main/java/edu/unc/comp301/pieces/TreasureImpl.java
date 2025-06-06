package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Treasure;

public class TreasureImpl extends APiece implements Treasure {
  public TreasureImpl(Posn posn) {
    super("treasure", "UNCMoney_Treasure.png", posn);
  }
}

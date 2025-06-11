package edu.unc.comp301.model.pieces.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.requirements.Treasure;

public class TreasureImpl extends APiece implements Treasure {
  public TreasureImpl(Posn posn) {
    super("treasure", "UNCMoney_Treasure.png", posn);
  }
}

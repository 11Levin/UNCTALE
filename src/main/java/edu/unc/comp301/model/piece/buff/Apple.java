package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;

public class Apple extends Buff {
  public Apple(Posn posn) {
    super(
        "apple",
        "buff/UNCApple_Buff.png",
        posn,
        new CollisionEvent(0, 0, 10, CollisionEvent.Result.PICK_ITEM));
  }
}

package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import javafx.geometry.Pos;

import java.util.List;

public class CollisionEvent {
  private final int points;
  private final Result res;
  private List<Posn> toUpdate;

  /**
   * Event taking a change in points, change in attack, change in health, and a collision result
   * @param points
   * @param res
   * @param toUpdate a list of positions involved with movement
   */
  public CollisionEvent(int points, Result res) {
    this.points = points;
    this.res = res;
  }

  public int getPoints() {
    return points;
  }

  public Result getResults() {
    return res;
  }

  public List<Posn> getToUpdate() {return toUpdate;}

  public enum Result {
    CONTINUE,
    GAME_OVER,
    NEXT_LEVEL,
    PICK_ITEM,
    ATTACK,
    HIT,
    BLOCK,
  }

  public void setToUpdate(List<Posn> toUpdate) {
    this.toUpdate = toUpdate;
  }
}

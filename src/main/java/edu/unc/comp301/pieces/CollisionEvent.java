package edu.unc.comp301.pieces;

public class CollisionEvent {
  private final int points;
  private final Result res;

  /**
   * Event taking a change in points, change in attack, change in health, and a collision result
   * @param points
   * @param res
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

  public enum Result {
    CONTINUE,
    GAME_OVER,
    NEXT_LEVEL,
    PICK_ITEM,
    ATTACK,
    HIT,
    BLOCK,
  }
}

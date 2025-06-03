package edu.unc.comp301.pieces;

public class CollisionEvent {
  private final int points;
  private final int attack;
  private final int health;
  private final Result res;

  /**
   * Event taking a change in points, change in attack, change in health, and a collision result
   * @param points
   * @param attack
   * @param health
   * @param res
   */
  public CollisionEvent(int points, int attack, int health, Result res) {
    this.points = points;
    this.attack = attack;
    this.health = health;
    this.res = res;
  }

  public int getPoints() {
    return points;
  }

  public int getHealth() {
    return health;
  }

  public int getAttack() {
    return attack;
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

package edu.unc.comp301.model.piece;

public class CollisionEvent {
  private final int points;
  private final int attack;
  private final int health;
  private final Result result;
  public static final CollisionEvent CONTINUE = new CollisionEvent(0, 0, 0, Result.CONTINUE);
  public static final CollisionEvent BLOCK = new CollisionEvent(0, 0, 0, Result.BLOCK);

  public CollisionEvent(int points, int attack, int health, Result result) {
    this.points = points;
    this.attack = attack;
    this.health = health;
    this.result = result;
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

  public Result getResult() {
    return result;
  }

  public enum Result {
    CONTINUE,
    NEXT_LEVEL,
    PICK_ITEM,
    ATTACK,
    HIT,
    BLOCK,
    GAME_OVER,
    TELEPORT,
  }

  @Override
  public String toString() {
    return "(points, health, attack, result) = "
        + "("
        + points
        + ", "
        + health
        + ", "
        + attack
        + ", "
        + result
        + ")";
  }
}

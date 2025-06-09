package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import javafx.geometry.Pos;

import java.util.List;

public class CollisionEvent {
  private final int points;
  private final int attack;
  private final int health;
  private final Result res;
  private List<Posn> toUpdate;

  /**
   * Event taking a change in points, change in attack, change in health, and a collision result
   * @param points
   * @param attack
   * @param health
   * @param res
   * @param toUpdate a list of positions involved with movement
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

  public List<Posn> getToUpdate() {return toUpdate;}

  public enum Result {
    GAME_START,
    CONTINUE,
    GAME_OVER,
    NEXT_LEVEL,
    PICK_ITEM,
    ATTACK,
    HIT,
    BLOCK,
  }

  @Override
  public String toString(){
    return "The move is "+res.name();
  }

  public void setToUpdate(List<Posn> toUpdate) {
    this.toUpdate = toUpdate;
  }
}

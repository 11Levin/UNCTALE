package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Enemy;

public class EnemyImpl extends Character implements Enemy {

  public EnemyImpl(String name, String resourcePath, int health) {
    super(name, resourcePath, health);
  }

  @Override
  public void shoot() {}

  @Override
  public void moveEnemy(int drow, int dcol) {}

  @Override
  public void attack() {}
}

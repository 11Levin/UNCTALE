package edu.unc.comp301.pieces.requirements;

import edu.unc.comp301.pieces.CollisionEvent;

public interface Enemy {
  void shoot();

  void moveEnemy(int drow, int dcol);

  void attack();
}

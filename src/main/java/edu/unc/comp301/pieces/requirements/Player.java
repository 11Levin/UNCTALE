package edu.unc.comp301.pieces.requirements;

import edu.unc.comp301.pieces.CollisionEvent;

public interface Player {
  void movePlayer(int drow, int dcol);

  void attack();
}

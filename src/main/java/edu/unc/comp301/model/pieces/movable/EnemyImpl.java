package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.requirements.Enemy;

public class EnemyImpl extends Character implements Enemy {
  public EnemyImpl(Posn posn, int health) {
    super("enemy", "UNCStudent_Enemy.png", posn, health);
  }
}

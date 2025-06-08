package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Enemy;

public class EnemyImpl extends Character implements Enemy {
  public EnemyImpl(Posn posn, int health) {
    super("enemy", "UNCStudent_Enemy.png", posn, health);
  }
}

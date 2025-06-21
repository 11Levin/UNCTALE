package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;

public interface MovableInterface extends Piece {
  int getHealth();

  void setHealth(int health);

  int getAttack();

  void setAttack(int attack);

  CollisionEvent collide(Piece other);

  void move(int drow, int dcol);

  void attack(int attack, MovablePiece other);
}

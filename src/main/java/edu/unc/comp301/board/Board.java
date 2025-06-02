package edu.unc.comp301.board;


import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Piece;

/** Defines the public operations on the game board. */
public interface Board {

  void init(int enemies, int treasures, int walls);

  int getWidth();

  int getHeight();

  Piece getPiece(Posn posn);

  CollisionEvent moveHero(int drow, int dcol);

  CollisionEvent moveEnemies();
}

package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);
  Posn moveRight(Posn move);
  Posn moveLeft(Posn move);
  Posn moveUp(Posn move);
  Posn moveDown(Posn move);
}

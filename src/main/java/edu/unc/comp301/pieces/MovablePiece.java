package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.Posn;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);

  Posn moveRight(Posn move, Board pos);

  Posn moveLeft(Posn move, Board pos);

  Posn moveUp(Posn move, Board pos);

  Posn moveDown(Posn move, Board pos);
}

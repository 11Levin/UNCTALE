package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.Posn;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);
}

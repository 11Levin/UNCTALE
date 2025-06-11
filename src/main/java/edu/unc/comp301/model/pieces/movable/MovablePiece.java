package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.pieces.CollisionEvent;
import edu.unc.comp301.model.pieces.Piece;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);
}

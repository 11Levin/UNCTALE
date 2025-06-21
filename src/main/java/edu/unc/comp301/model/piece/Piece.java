package edu.unc.comp301.model.piece;

import edu.unc.comp301.model.board.Posn;

public interface Piece {
  enum Type {
    MOVABLE,
    IMMOVABLE,
    BUFF,
  }

  String getName();

  Posn getPosn();

  void setPosn(Posn posn);

  String getResourcePath();

  Type getType();
}

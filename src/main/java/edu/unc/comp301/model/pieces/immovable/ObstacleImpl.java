package edu.unc.comp301.model.pieces.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.requirements.Obstacle;

public class ObstacleImpl extends APiece implements Obstacle {
  public ObstacleImpl(Posn posn) {
    super("obstacle", "UNCRam_Obstacle.png", posn);
  }
}

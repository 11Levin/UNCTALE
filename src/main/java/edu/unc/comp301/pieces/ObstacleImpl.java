package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Obstacle;

public class ObstacleImpl extends APiece implements Obstacle {
  public ObstacleImpl(Posn posn) {
    super("obstacle", "UNCRam_Obstacle.png", posn);
  }
}

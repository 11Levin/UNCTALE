package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Exit;

public class ExitImpl extends APiece implements Exit {

  public ExitImpl(String name, String resourcePath) {
    super(name, resourcePath);
  }

  @Override
  public void playerArrived(Piece Other) {}
}

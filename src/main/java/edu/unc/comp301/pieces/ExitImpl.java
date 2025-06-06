package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.requirements.Exit;

public class ExitImpl extends APiece implements Exit {

  public ExitImpl(Posn posn) {
    super("exit", "UNCLogo_Exit.png", posn);
  }

}

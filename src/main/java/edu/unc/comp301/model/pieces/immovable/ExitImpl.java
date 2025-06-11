package edu.unc.comp301.model.pieces.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.requirements.Exit;

public class ExitImpl extends APiece implements Exit {

  public ExitImpl(Posn posn) {
    super("exit", "UNCLogo_Exit.png", posn);
  }
}

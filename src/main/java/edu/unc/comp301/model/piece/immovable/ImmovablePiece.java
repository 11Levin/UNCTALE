package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.APiece;

public abstract class ImmovablePiece extends APiece {
    public ImmovablePiece(String name, String resourcePath, Posn posn) {
        super(name, resourcePath, posn, Type.IMMOVABLE);
    }
}

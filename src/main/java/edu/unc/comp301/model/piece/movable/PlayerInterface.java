package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.buff.Buff;

import java.util.*;

public interface PlayerInterface extends MovableInterface {
    List<String> getBuffs();
}

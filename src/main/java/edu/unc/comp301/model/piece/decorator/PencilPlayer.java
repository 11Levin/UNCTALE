package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.piece.movable.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

public class PencilPlayer extends PlayerDecorator {
    public PencilPlayer(PlayerInterface playerInterface) {
        super(playerInterface);
    }

    @Override
    public List<String> getBuffs() {
        List<String> rtn = new ArrayList<>(playerInterface.getBuffs());
        rtn.add("pencil_item");
        return rtn;
    }
}

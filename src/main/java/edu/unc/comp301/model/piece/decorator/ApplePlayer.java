package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.piece.PlayerStats;
import edu.unc.comp301.model.piece.buff.Apple;
import edu.unc.comp301.model.piece.movable.PlayerInterface;
import java.util.*;

public class ApplePlayer extends PlayerDecorator{
    public ApplePlayer(PlayerInterface playerInterface) {
        super(playerInterface);
    }

    @Override
    public List<String> getBuffs() {
        List<String> rtn = new ArrayList<>(playerInterface.getBuffs());
        rtn.add("apple_item");
        return rtn;
    }

}

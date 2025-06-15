package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.piece.movable.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerDecorator implements PlayerInterface {
    private final PlayerInterface playerInterface;
    private Character buff;

    public PlayerDecorator(PlayerInterface playerInterface, Character buff) {
        this.playerInterface = playerInterface;
        this.buff = buff;
    }

    @Override
    public List<Character> getBuffs() {
        List<Character> rtn = new ArrayList<>(playerInterface.getBuffs());
        rtn.add(buff);
        return rtn;
    }
}
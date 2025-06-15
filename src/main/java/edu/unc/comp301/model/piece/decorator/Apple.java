package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.piece.movable.PlayerInterface;

public class Apple extends PlayerDecorator{
    public Apple(PlayerInterface playerInterface) {
        super(playerInterface, 'a');
    }
}

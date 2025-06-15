package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.piece.movable.PlayerInterface;

public class Homework extends PlayerDecorator{
    public Homework(PlayerInterface playerInterface) {
        super(playerInterface, 'h');
    }
}

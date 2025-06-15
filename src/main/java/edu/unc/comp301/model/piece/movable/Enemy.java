package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.exception.InvalidMoveException;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;

public abstract class Enemy extends MovablePiece {
    private int speed;

    public Enemy(String name, String resourcePath, Posn posn, int health, int attack,int speed) {
        super(name, resourcePath, posn, health, attack);
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }


    @Override
    public CollisionEvent collide(Piece other) {
        if (other == null) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
        CollisionEvent block = new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
        switch (other.getType()) {
            case Type.IMMOVABLE, Type.BUFF:
                return block;
            case Type.MOVABLE:
                if ((other.getName().equals("player"))&&this.getName().equals("bullet")) {
                    return new CollisionEvent(0, 0, 0, CollisionEvent.Result.HIT);
                } else if(other.getName().equals("player")){
                    return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
                } else {
                    return block;
                }
            default:
                throw new InvalidMoveException("enemy can't move");
        }
    }
}

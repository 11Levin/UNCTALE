package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.buff.Buff;
import edu.unc.comp301.model.piece.immovable.ImmovablePiece;
import java.util.*;

public class Player extends MovablePiece implements PlayerInterface{
    public Player(Posn posn, int health, int attack) {
        super("player", "movable/UNCPlayer.png", posn, health, attack);
    }

    @Override
    public CollisionEvent collide(Piece other) {
        if (other == null) return CollisionEvent.CONTINUE;
        switch (other.getType()) {
            case IMMOVABLE:
                ImmovablePiece temp = (ImmovablePiece) other;
                return temp.getCollisionEvent();
            case BUFF:
                Buff buff = (Buff) other;
                return buff.getCollisionEvent();
            case MOVABLE:
                if(other.getName().equals("bullet")){
                    return new CollisionEvent(0, 0, 0, CollisionEvent.Result.HIT);
                }
                return new CollisionEvent(0, 0, 0, CollisionEvent.Result.ATTACK);
            default:
                return CollisionEvent.CONTINUE;
        }
    }

    public List<String> getBuffs() {
        return new ArrayList<>();
    }
}
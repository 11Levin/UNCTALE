package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;

public class Player extends MovablePiece {
    public Player(Posn posn, int health, int attack) {
        super("player", "movable/UNCPlayer.png", posn, health, attack);
    }

    @Override
    public CollisionEvent collide(Piece other) {
        if (other == null) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
        switch (other.getName()) {
            case "treasure":
                return new CollisionEvent(10, 0, 0, CollisionEvent.Result.PICK_ITEM);
            case "obstacle":
                return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
            case "pencil":
                return new CollisionEvent(0, 5, 0, CollisionEvent.Result.PICK_ITEM);
            case "apple":
                return new CollisionEvent(0, 0, 5, CollisionEvent.Result.PICK_ITEM);
            case "homework":
                return new CollisionEvent(0, -5, -10, CollisionEvent.Result.PICK_ITEM);
            case "enemy":
                return new CollisionEvent(0, 0, 0, CollisionEvent.Result.ATTACK);
            case "exit":
                return new CollisionEvent(20, 0, 0, CollisionEvent.Result.NEXT_LEVEL);
            default:
                return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
        }
    }
}
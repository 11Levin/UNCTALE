package edu.unc.comp301.model.piece.immovable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.APiece;
import edu.unc.comp301.model.piece.CollisionEvent;

public abstract class ImmovablePiece extends APiece {
    private final CollisionEvent collisionEvent;

    public ImmovablePiece(String name, String resourcePath, Posn posn) {
        this(name, resourcePath, posn, new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE));
    }

    public ImmovablePiece(String name, String resourcePath, Posn posn, CollisionEvent collisionEvent) {
        super(name, resourcePath, posn, Type.IMMOVABLE);
        this.collisionEvent = collisionEvent;
    }

    public CollisionEvent getCollisionEvent() {return collisionEvent;}
}

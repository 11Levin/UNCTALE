package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.APiece;
import edu.unc.comp301.model.piece.CollisionEvent;

public abstract class Buff extends APiece {
    private final CollisionEvent collisionEvent;

    /**
     * Creates a buff
     *
     * @param name         the buff's name
     * @param resourcePath (name).png or (name).jpg
     * @param posn         the buff's position
     */
    public Buff(String name, String resourcePath, Posn posn, CollisionEvent collisionEvent) {
        super(name, resourcePath, posn, Type.BUFF);
        this.collisionEvent = collisionEvent;
    }

    public CollisionEvent getCollisionEvent() {
        return collisionEvent;
    }
}

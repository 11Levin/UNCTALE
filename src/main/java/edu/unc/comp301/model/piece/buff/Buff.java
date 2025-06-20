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
     * @param collisionEvent the resulting collision event from picking up the buff
     */
    public Buff(String name, String resourcePath, Posn posn, CollisionEvent collisionEvent) {
        super(name, resourcePath, posn, Type.BUFF);
        this.collisionEvent = collisionEvent;
    }

    /**
     * @return the collision event from a movable picking up this buff
     */
    public CollisionEvent getCollisionEvent() {
        return collisionEvent;
    }
}

package edu.unc.comp301.model.piece.movable;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.APiece;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;

public abstract class MovablePiece extends APiece implements MovableInterface {
    private int health;
    private int attack;


    public MovablePiece(String name, String resourcePath, Posn posn, int health, int attack) {
        super(name, resourcePath, posn, Type.MOVABLE);
        this.health = health;
        this.attack = attack;
    }

    /**
     * Simulates a collision as if this were at the same position as other
     *
     * @param other the piece to collide
     * @return the result of the collision
     */
    public abstract CollisionEvent collide(Piece other);

    /**
     * Unsafe to move directly - should move this through Board and Controller
     *
     * @param drow change in row
     * @param dcol change in col
     */
    public void move(int drow, int dcol) {
        setPosn(new Posn(getPosn().getRow() + drow, getPosn().getCol() + dcol));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = Math.max(attack, 0);
    }

    public void attack(int attack, MovablePiece Opponent) {
        Opponent.setHealth(Opponent.getHealth() - attack);
    }
}
package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.Posn;

public class Character extends APiece implements MovablePiece{
    private int health;
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public Character(String name, String resourcePath, int health){
        super(name,resourcePath);
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
        if (health < 0){
            this.health = 0;
        }
    }

    public boolean validMove(CollisionEvent movement){
        if ((movement.getResults().equals(CollisionEvent.Result.BLOCK) || (movement.getResults().equals(CollisionEvent.Result.ATTACK)) || (movement.getResults().equals(CollisionEvent.Result.HIT)))){
            return false;
        }
        return true;
    }

    public CollisionEvent collide(Piece other){
        Posn collision = other.getPosn();
        if (collision == null){
            return new CollisionEvent(0, CollisionEvent.Result.CONTINUE);
        }
        return new CollisionEvent(0, this.determineCollision(other));
    }
    public Posn moveRight(Posn move, Board pos){
        Posn movingTo = new Posn(move.getRow(),move.getCol()+1);
        Piece collide = pos.getPiece(movingTo);
        CollisionEvent type = collide(collide);
        if(validMove(type)){
            return new Posn(move.getRow(),move.getCol()+1);
        }
        return new Posn(move.getRow(),move.getCol());
    }
    public Posn moveLeft(Posn move, Board pos){
        Posn movingTo = new Posn(move.getRow(),move.getCol()-1);
        Piece collide = pos.getPiece(movingTo);
        CollisionEvent type = collide(collide);
        if(validMove(type)){
            return new Posn(move.getRow(),move.getCol()-1);
        }
        return new Posn(move.getRow(),move.getCol());

    }
    public Posn moveUp(Posn move, Board pos){
        Posn movingTo = new Posn(move.getRow()-1,move.getCol());
        Piece collide = pos.getPiece(movingTo);
        CollisionEvent type = collide(collide);
        if(validMove(type)){
            return new Posn(move.getRow()-1,move.getCol());
        }
        return new Posn(move.getRow(),move.getCol());
    }
    public Posn moveDown(Posn move, Board pos){
        Posn movingTo = new Posn(move.getRow()+1,move.getCol());
        Piece collide = pos.getPiece(movingTo);
        CollisionEvent type = collide(collide);
        if(validMove(type)){
            return new Posn(move.getRow()+1,move.getCol());
        }
        return new Posn(move.getRow(),move.getCol());
    }
}

package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Posn;

public class Character extends APiece implements MovablePiece{
    private int health;
    public Character(String name, String resourcePath, int health){
        super(name,resourcePath);
        this.health = health;
    }

    public CollisionEvent collide(Piece other){
        return null;
    }
    public Posn moveRight(Posn move){
        return new Posn(move.getRow(),move.getCol()+1);
    }
    public Posn moveLeft(Posn move){
        return new Posn(move.getRow(),move.getCol()-1);
    }
    public Posn moveUp(Posn move){
        return new Posn(move.getRow()-1,move.getCol());
    }
    public Posn moveDown(Posn move){
        return new Posn(move.getRow()+1,move.getCol());
    }
}

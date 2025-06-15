package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.buff.Buff;
import edu.unc.comp301.model.piece.movable.MovablePiece;
import edu.unc.comp301.model.piece.movable.Player;
import edu.unc.comp301.model.piece.movable.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

public class PlayerDecorator implements PlayerInterface {
    protected final PlayerInterface playerInterface;

    public PlayerDecorator(PlayerInterface playerInterface) {
        this.playerInterface = playerInterface;
    }

    @Override
    public List<String> getBuffs() {
        return new ArrayList<>();
    }

    @Override
    public int getHealth() {
        return playerInterface.getHealth();
    }

    @Override
    public void setHealth(int health) {
        playerInterface.setHealth(health);
    }

    @Override
    public int getAttack() {
        return playerInterface.getAttack();
    }

    @Override
    public void setAttack(int attack) {
        playerInterface.setAttack(attack);
    }

    @Override
    public CollisionEvent collide(Piece other) {
        return playerInterface.collide(other);
    }

    @Override
    public void move(int drow, int dcol) {
        playerInterface.move(drow, dcol);
    }

    @Override
    public void attack(int attack, MovablePiece other) {
        playerInterface.attack(attack, other);
    }

    @Override
    public String getName() {
        return playerInterface.getName();
    }

    @Override
    public Posn getPosn() {
        return playerInterface.getPosn();
    }

    @Override
    public void setPosn(Posn posn) {
        playerInterface.setPosn(posn);
    }

    @Override
    public String getResourcePath() {
        return playerInterface.getResourcePath();
    }

    @Override
    public Type getType() {
        return playerInterface.getType();
    }
}
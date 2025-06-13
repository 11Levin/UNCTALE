package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;

import java.util.*;

public class Model implements Subject{
    private List<Observer> views = new ArrayList<>();
    private Board board;

    // player stats
    private int score;
    private int highScore;
    private int level = 1;

    public enum STATUS {
        GAME_START,
        GAME_CONTINUE,
        GAME_END,
    }

    private STATUS status = STATUS.GAME_END;

    public Model(int width, int height) {
        board = new Board(width, height);
    }

    public Model(Board board) {
        this.board = board;
    }

    public int getWidth() {
        return board.getWidth();
    }

    public int getHeight() {
        return board.getHeight();
    }

    public int getCurrScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLevel() {
        return level;
    }

    public Piece get(Posn p) {
        if (board == null) return null;
        int r = p.getRow();
        int c = p.getCol();
        if (r < 0 || r >= board.getHeight()) return null;
        if (c < 0 || c >= board.getWidth()) return null;
        return board.getPiece(p);
    }

    public STATUS getStatus() {
        return status;
    }

    public void startGame() {
        score = 0;
        level = 1;
        status = STATUS.GAME_CONTINUE;
    }

    public void endGame() {
        score = 0;
        level = 1;
        status = STATUS.GAME_END;
        highScore = Math.max(highScore, score);
    }

    public void moveUp() {
    }

    public void moveDown() {
    }

    public void moveLeft() {
    }

    public void moveRight() {
    }

    private void move(int drow, int dcol) {
        CollisionEvent collisionEvent = board.movePlayer(drow, dcol);
        score += collisionEvent.getPoints();

    }

    @Override
    public void addView(Observer view) {

    }

    private void updateViews() {

    }
}

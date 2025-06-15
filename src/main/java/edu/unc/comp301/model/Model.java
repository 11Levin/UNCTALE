package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.GameStats;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.PlayerStats;

import java.util.*;

public class Model implements Subject {
    public enum STATUS {
        GAME_START,
        GAME_CONTINUE,
        GAME_END,
    }

    private final List<Observer> views = new ArrayList<>();
    private final Board board;
    private volatile STATUS status = STATUS.GAME_END;
    private PlayerStats playerStats = new PlayerStats(20, 50);

    // player stats
    private int score;
    private int highScore;
    private int level = 1;

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
        board.init(level, playerStats);
        status = STATUS.GAME_CONTINUE;
        updateViews();
    }

    public void endGame() {
        score = 0;
        level = 1;
        status = STATUS.GAME_END;
        highScore = Math.max(highScore, score);
    }

    public void moveUp() {
        move(-1, 0);
    }

    public void moveDown() {
        move(1, 0);
    }

    public void moveLeft() {
        move(0, -1);
    }

    public void moveRight() {
        move(0, 1);
    }

    public void spawnBullet() {
        board.spawnBullet();
    }

    private void move(int drow, int dcol) {
        CollisionEvent collisionEvent = board.movePlayer(drow, dcol);
        if (collisionEvent.getResult() == CollisionEvent.Result.NEXT_LEVEL) {
            playerStats = board.getPlayerStats();
            board.init(++level, playerStats);
        }
        score += collisionEvent.getPoints();
    }

    @Override
    public void addView(Observer view) {
        views.add(view);
    }

    public void updateViews() {
        playerStats = board.getPlayerStats();
        for (Observer observer : views) {
            observer.update(new GameStats(level, score, playerStats.getAttack(), playerStats.getHealth()));
        }
    }

    public String[] getToDraw() {
        return board.getToDraw();
    }
}

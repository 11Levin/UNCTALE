package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.GameStats;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.PlayerStats;
import edu.unc.comp301.view.Observer;
import edu.unc.comp301.view.Subject;
import edu.unc.comp301.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Model implements Subject {
    public enum STATUS {
        GAME_START,
        GAME_CONTINUE,
        GAME_END,
        GAME_INSTRUCTIONS,
    }

    private final List<Observer> views = new ArrayList<>();
    private final Board board;
    private volatile STATUS status = STATUS.GAME_END;
    private final PlayerStats defaultStats = new PlayerStats(20, 100, new HashSet<>());
    private PlayerStats playerStats = defaultStats;
    private boolean winGame;

    // player stats
    private int score;
    private int highScore;
    private int lastScore;
    private int level = 1;
    private int difficulty;

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

    public int getLastScore() {
        return lastScore;
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

    public void startGame(int difficulty) {
        this.difficulty = difficulty;
        lastScore = score;
        score = 0;
        level = 1;
        winGame = false;
        board.init(level, defaultStats);
        status = STATUS.GAME_CONTINUE;
        renderViews();
    }

    public void endGame() {
        playerStats = board.getPlayerStats();
        status = STATUS.GAME_END;
        highScore = Math.max(highScore, score);
        updateViews();
    }

    public void winGame() {
        playerStats = board.getPlayerStats();
        status = STATUS.GAME_END;
        highScore = Math.max(highScore, score);
        winGame = true;
        updateViews();
    }

    public void showInstructions() {
        status = STATUS.GAME_INSTRUCTIONS;
        updateViews();
    }

    public void showHomeScreen() {
        status = STATUS.GAME_START;
        updateViews();
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

    public void spawnBullet(int tick) {
        board.spawnBullet(tick);
    }

    public int getDifficulty() {
        board.setDifficulty(this.difficulty);
        return this.difficulty;
    }

    private void move(int drow, int dcol) {
        CollisionEvent collisionEvent = board.movePlayer(drow, dcol);
        score += collisionEvent.getPoints();
        if (collisionEvent.getResult() == CollisionEvent.Result.NEXT_LEVEL && level != 5) {
            playerStats = board.getPlayerStats();
            board.init(++level, playerStats);
        } else if (collisionEvent.getResult() == CollisionEvent.Result.GAME_OVER) {
            playerStats = board.getPlayerStats();
            endGame();
        } else if (collisionEvent.getResult() == CollisionEvent.Result.NEXT_LEVEL && level == 5) {
            playerStats = board.getPlayerStats();
            winGame();
        }
    }

    @Override
    public void addView(Observer view) {
        views.add(view);
    }

    public void updateViews() {
        playerStats = board.getPlayerStats();
        for (Observer observer : views) {
            observer.update(new GameStats(level, score, highScore, lastScore, playerStats.getAttack(), playerStats.getHealth(), playerStats.getBuffs(), winGame));
        }
    }

    /**
     * For re-rendering views if screen is resized
     */
    public void renderViews() {
        for (Observer view : views) {
            ((View) view).render();
        }
    }

    public String[] getToDraw(int verticalRadius, int horizontalRadius) {
        return board.getToDraw(verticalRadius, horizontalRadius);
    }
}

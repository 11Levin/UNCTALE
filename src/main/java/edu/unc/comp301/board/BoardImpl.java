package edu.unc.comp301.board;

import edu.unc.comp301.pieces.*;
import edu.unc.comp301.pieces.requirements.Enemy;

import java.util.*;

public class BoardImpl implements Board{
    private Piece[][] board;
    private int width;
    private int height;
    public BoardImpl(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        board = new Piece[height][width];
    }

    public BoardImpl(Piece[][] board) {
        this.board = board;
        width = board[0].length;
        height = board.length;
    }

    @Override
    public void init(int enemies, int treasures, int walls) {
        final int NUM_PLAYERS = 1;
        final int NUM_EXITS = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = null;
            }
        }
        if (enemies + treasures + walls + NUM_PLAYERS + NUM_EXITS > width * height) {
            throw new IllegalArgumentException("Number of grid spots exceeded");
        }
        List<Piece> toShuffle = new ArrayList<>();
        for (int i = 0; i < enemies; i++) {
            toShuffle.add(new EnemyImpl("enemy", "resources/UNCRamses_Enemy.png", 100));
        }
        for (int i = 0; i < treasures; i++) {
            toShuffle.add(new TreasureImpl("treasure", "resources/UNCMoney_Treasure.png"));
        }
        for (int i = 0; i < walls; i++) {
            toShuffle.add(new ObstacleImpl("obstacle", "resources/UNCRam_Obstacle.png"));
        }
        toShuffle.add(new ExitImpl("exit", "resources/UNCLogo_Exit.png"));
        toShuffle.add(new PlayerImpl("player", "resources/UNCPlayer.png", 100));
        while (toShuffle.size() < width * height) {
            toShuffle.add(null);
        }
        Collections.shuffle(toShuffle);
        int count = 0;
        while (count < width * height) {
            board[count / width][count % width] = toShuffle.get(count);
            count++;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Piece get(Posn posn) {
        return board[posn.getRow()][posn.getCol()];
    }

    @Override
    public CollisionEvent moveHero(int drow, int dcol) {
        return null;
    }

    @Override
    public CollisionEvent moveEnemies() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Piece[] row : board) {
            for (Piece piece : row) {
                sb.append(piece);
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

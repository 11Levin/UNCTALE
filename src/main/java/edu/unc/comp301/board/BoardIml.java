package edu.unc.comp301.board;

import edu.unc.comp301.Exceptions.InvalidBoardDimensionsException;
import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Piece;
import java.util.*;

public class BoardIml implements Board{
    private List<List<Piece>> board;
    private int rows;
    private int cols;
    public BoardIml(int rows, int cols) {
        if (rows <= 0) {
            throw new InvalidBoardDimensionsException("Number of rows needs to be positive.");
        } else if (cols <= 0) {
            throw new InvalidBoardDimensionsException("Number of cols need to be positive");
        }
        board = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            List<Piece> row = new ArrayList<>();
            for (int j = 0; j < rows; j++) row.add(null);
        }
        this.rows = rows;
        this.cols = cols;
        board.add(null);
    }

    public BoardIml(List<List<Piece>> board) {
        this.board = board;
        if (board == null) throw new InvalidBoardDimensionsException("board cannot be null");
        if (board.size() == 0) throw new InvalidBoardDimensionsException("board cannot be nonempty");
        int rowSize = board.get(0).size();
        if (rowSize == 0) throw new InvalidBoardDimensionsException("columns cannot be empty");
        for (List<Piece> row : board) {
            if (row == null) throw new InvalidBoardDimensionsException("rows cannot be null");
            if (row.size() != rowSize) throw new InvalidBoardDimensionsException("board needs to be rectangular");
        }
        rows = board.size();
        cols = board.get(0).size();
    }



    @Override
    public void init(int enemies, int treasures, int walls) {
    }

    @Override
    public int getWidth() {
        return cols;
    }

    @Override
    public int getHeight() {
        return rows;
    }

    @Override
    public Piece get(Posn posn) {
        return null;
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(board.get(r).get(c));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

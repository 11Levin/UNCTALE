package edu.unc.comp301;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.pieces.Character;
import edu.unc.comp301.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Piece[][] grid = new Piece[3][4];
        grid[0][0] = new Character("a", "a", 0);
        grid[1][1] = new Character("b", "b", 0);
        grid[2][2] = new Character("c", "c", 0);
        Board board = new BoardImpl(grid);
        board.init(4, 2, 4);
        System.out.println(board);
        System.out.println(board.getHeight());
    }
}
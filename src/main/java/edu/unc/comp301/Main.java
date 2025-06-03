package edu.unc.comp301;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.pieces.Character;
import edu.unc.comp301.pieces.ObstacleImpl;
import edu.unc.comp301.pieces.Piece;
import edu.unc.comp301.pieces.requirements.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Run code from here and open the terminal. Type H for help.
 */
public class Main {
  public static void main(String[] args) throws FileNotFoundException {
    BoardImpl board = new BoardImpl(1, 1);
    board.init(1);
    System.out.println(board);
    while (board.makeMove());
  }
}

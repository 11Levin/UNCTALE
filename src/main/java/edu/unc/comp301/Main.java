//package edu.unc.comp301;
//
//import edu.unc.comp301.exceptions.LevelNotLoadedException;
//import edu.unc.comp301.board.BoardImpl;
//
//import java.io.FileNotFoundException;
//
///**
// * Run code from here and open the terminal. Type H for help.
// */
//public class Main {
//  public static void main(String[] args) {
//    BoardImpl board = new BoardImpl(1, 1);
//    try {
//      board.init(1);
//    } catch (FileNotFoundException e) {
//      throw new LevelNotLoadedException("level couldn't be loaded");
//    }
//    System.out.println(board);
//    while (board.makeMove());
//  }
//}

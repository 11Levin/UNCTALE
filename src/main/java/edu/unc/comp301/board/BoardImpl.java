package edu.unc.comp301.board;

import edu.unc.comp301.pieces.*;
import edu.unc.comp301.pieces.requirements.Enemy;

import java.io.File;
import java.io.FileNotFoundException;
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

    private void clearBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = null;
            }
        }
    }

    @Override
    public void init(int enemies, int treasures, int walls) {
        final int NUM_PLAYERS = 1;
        final int NUM_EXITS = 1;
        clearBoard();
        if (enemies + treasures + walls + NUM_PLAYERS + NUM_EXITS > width * height) {
            throw new IllegalArgumentException("Number of grid spots exceeded");
        }
        List<Piece> toShuffle = new ArrayList<>();
        for (int i = 0; i < enemies; i++) {
            toShuffle.add(nameToPiece("enemy"));
        }
        for (int i = 0; i < treasures; i++) {
            toShuffle.add(nameToPiece("treasure"));
        }
        for (int i = 0; i < walls; i++) {
            toShuffle.add(nameToPiece("obstacle"));
        }
        toShuffle.add(nameToPiece("exit"));
        toShuffle.add(nameToPiece("player"));
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

    private Piece nameToPiece(String name) {
        return switch (name) {
            case "obstacle" -> new ObstacleImpl("obstacle", "resources/UNCRam_Obstacle.png");
            case "enemy" -> new EnemyImpl("enemy", "resources/UNCRamses_Enemy.png", 100);
            case "treasure" -> new TreasureImpl("treasure", "resources/UNCMoney_Treasure.png");
            case "player" -> new PlayerImpl("player", "resources/UNCPlayer", 100);
            case "exit" -> new ExitImpl("exit", "resources/UNCLogo_Exit.png");
            default -> null;
        };
    }

    public void init(int level) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/UNCLevel_1"));
        System.out.println(nameToPiece("obstacle"));
        height = scanner.nextInt();
        width = scanner.nextInt();
        board = new Piece[height][width];
        String line;
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            line = scanner.nextLine().strip().replace(" ", "");
            if (line.charAt(line.length() - 1) == ',') line = line.substring(0, line.length() - 1);
//            System.out.println(line);
            String[] splitted = line.split(",");
            for (int j = 0; j < width; j++) {
                board[i][j] = nameToPiece(splitted[j]);
            }
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

//    public void makeMove() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Make a move (type H for help): ");
//        String res = scanner.next();
//    while (true) {
//      switch (res) {
//        case "H":
//          System.out.println("H: help");
//          System.out.println("W: move up");
//          System.out.println("A: move left");
//          System.out.println("S: move down");
//          System.out.println("D: move right");
//          System.out.println("E: exit");
//        case "E":
//          System.out.println("Game exited");
//          return;
//        default:
//          System.out.println("Invalid move");
//      }
//    }
//  }
//}

package edu.unc.comp301.board;

import edu.unc.comp301.exceptions.InvalidMoveException;
import edu.unc.comp301.exceptions.LevelNotLoadedException;
import edu.unc.comp301.exceptions.PlayerAlreadyFoundException;
import edu.unc.comp301.pieces.*;
import edu.unc.comp301.ui.GameView;
import edu.unc.comp301.ui.View;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BoardImpl implements Board {
  private Piece[][] board;
  private int width;
  private int height;
  private PlayerImpl player;
  private int points = 0;
  private int level = 1;
  private GameView gameView;

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

  public void setGameView(GameView gameView) {
    this.gameView = gameView;
  }

  /**
   * Sets everything in the board to null
   */
  private void clearBoard() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = null;
      }
    }
  }

  /**
   * Initializes a random grid with a specified number of enemies, treasures, and walls, and throws
   * an exception if this layout is not possible
   * @param enemies number of enemies to spawn
   * @param treasures number of treasures to spawn
   * @param walls number of walls to spawn
   */
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
    player = (PlayerImpl) nameToPiece("player");
    toShuffle.add(player);
    while (toShuffle.size() < width * height) {
      toShuffle.add(null);
    }
    Collections.shuffle(toShuffle);
    int count = 0;
    while (count < width * height) {
      int r = count / width;
      int c = count % width;
      board[r][c] = toShuffle.get(count);
      board[r][c].setPosn(new Posn(r, c));
      count++;
    }
  }

  /**
   * gives a Piece object from a string representing the piece.
   * Ex: "player" will become a PlayerImpl object.
   * @param name name of piece
   * @param posn position of piece
   * @return the created piece
   */
  private Piece nameToPiece(String name, Posn posn) {
    return switch (name) {
      case "obstacle" -> new ObstacleImpl(posn);
      case "enemy" -> new EnemyImpl(posn, 100);
      case "treasure" -> new TreasureImpl(posn);
      case "player" -> new PlayerImpl(posn, 100);
      case "exit" -> new ExitImpl(posn);
      default -> null;
    };
  }

  /**
   * Same behavior as nameToPiece except position is initialized to (0, 0)
   * @param name
   * @return
   */
  private Piece nameToPiece(String name) {
    return nameToPiece(name, new Posn(0, 0));
  }

  /**
   * Generates a specified level
   * @param level the level to generate (positive integer)
   * @throws FileNotFoundException
   */
  public void init(int level) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File("resources/UNCLevel_" + level));
    System.out.println("Loading level " + level);
    height = scanner.nextInt();
    width = scanner.nextInt();
    board = new Piece[height][width];
    String line;
    scanner.nextLine();
    boolean playerFound = false;
    for (int i = 0; i < height; i++) {
      line = scanner.nextLine().strip().replace(" ", "");
      if (line.charAt(line.length() - 1) == ',') line = line.substring(0, line.length() - 1);
      //            System.out.println(line);
      String[] splitted = line.split(",");
      for (int j = 0; j < width; j++) {
        board[i][j] = nameToPiece(splitted[j], new Posn(i, j));
        if (splitted[j].equals("player")) {
          if (playerFound) throw new PlayerAlreadyFoundException("board cannot have two players");
          playerFound = true;
          player = (PlayerImpl) board[i][j];
        }
      }
    }
    if (gameView != null) {
      System.out.println("Rendered level");

    }
  }

  /**
   * Gives the width or number of columns of the grid
   * @return
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Gives the height or number of rows of the grid
   * @return
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Accesses the Piece at the specified position on the board
   * @param posn
   * @return
   */
  @Override
  public Piece getPiece(Posn posn) {
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
        if (piece == null){
          sb.append(" ");
        }else{
          sb.append(piece);
        }

        sb.append(' ');
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  /**
   * Moves the player in a specified direction
   * @param dir one of WASD
   * @return a list of board positions involved with the player
   * (touching player for example)
   */
  public CollisionEvent move(KeyCode dir) {
    CollisionEvent collisionEvent;
    Posn prevPosn = player.getPosn();
    // move the player
    switch (dir){
      case KeyCode.W:
        collisionEvent = player.moveUp(this);
        break;
      case KeyCode.A:
        collisionEvent = player.moveLeft(this);
        break;
      case KeyCode.S:
        collisionEvent = player.moveDown(this);
        break;
      case KeyCode.D:
        collisionEvent = player.moveRight(this);
        break;
      default:
        throw new InvalidMoveException("Move should be WASD");
    }
    if (!player.getPosn().equals(prevPosn)) {
      board[prevPosn.getRow()][prevPosn.getCol()] = null;
      board[player.getPosn().getRow()][player.getPosn().getCol()] = player;
    }

    // handle collision types
    switch (collisionEvent.getResults()) {
      case CollisionEvent.Result.PICK_ITEM:
        setPoints(getPoints() + collisionEvent.getPoints());
        System.out.println("Treasure collected.");
        break;
      case CollisionEvent.Result.NEXT_LEVEL:
        System.out.println("Exit entered.");
        level++;
        setPoints(getPoints() + collisionEvent.getPoints());
        try {
          init(level);
          break;
        } catch (FileNotFoundException e){
          throw new LevelNotLoadedException("level cannot be loaded");
        }
    }
    List<Posn> toUpdate = new ArrayList<>();
    toUpdate.add(player.getPosn());
    toUpdate.add(prevPosn);
    collisionEvent.setToUpdate(toUpdate);
    return collisionEvent;
  }

  /**
   * Sets the player's points to a nonnegative value
   * @param points
   * @return if the change was made
   */
  public boolean setPoints(int points) {
    if (points < 0) return false;
    this.points = points;
    return true;
  }

  /**
   * Accesses the player's points
   * @return
   */
  public int getPoints() {
    return points;
  }

  /**
   * Sets the level (positive integer)
   * @param level
   * @return
   */
  public boolean setLevel(int level) {
    if (level <= 0) return false;
    this.level = level;
    return true;
  }

  /**
   * Gives the current level
   * @return
   */
  public int getLevel() {
    return level;
  }

  public int getHealth() {
    if (player == null) return 0;
    return player.getHealth();
  }

}

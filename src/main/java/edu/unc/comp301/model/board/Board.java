package edu.unc.comp301.model.board;
import edu.unc.comp301.model.exception.LevelNotLoadedException;
import edu.unc.comp301.model.exception.PieceOutOfBoundsException;
import edu.unc.comp301.model.exception.PlayerAlreadyFoundException;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.buff.Apple;
import edu.unc.comp301.model.piece.buff.Homework;
import edu.unc.comp301.model.piece.buff.Pencil;
import edu.unc.comp301.model.piece.immovable.Exit;
import edu.unc.comp301.model.piece.immovable.Treasure;
import edu.unc.comp301.model.piece.movable.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Board {
  // board info
  private Piece[][] board;
  private int width;
  private int height;

  // entity info
  private Player player;
  private ArrayList<Enemy> enemyList = new ArrayList<>();

  /**
   * Initializes an empty board with size (height, width)
   * @param width number of columns
   * @param height number of rows
   */
  public Board(int width, int height) {
    this.width = Math.max(1, width);
    this.height = Math.max(1, height);
    board = new Piece[height][width];
    try {
      loadKey();
    } catch (FileNotFoundException e) {
      throw new LevelNotLoadedException("invalid key to load map");
    }
  }

  public Board(Piece[][] board) {
    this.board = board;
    width = board[0].length;
    height = board.length;
    try {
      loadKey();
    } catch (FileNotFoundException e) {
      throw new LevelNotLoadedException("invalid key to load map");
    }
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
    player = (Player) nameToPiece("player");
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
      case "obstacle" -> new edu.unc.comp301.model.piece.immovable.Obstacle(posn);
      case "enemy" -> new Student(posn, 100);
      case "student" -> new Student(posn, 100);
      case "professor" -> new Professor(posn, 120);
      case "treasure" -> new Treasure(posn);
      case "player" -> new Player(posn, 100, 20);
      case "exit" -> new Exit(posn);
      case "apple" -> new Apple(posn);
      case "pencil" -> new Pencil(posn);
      case "homework" -> new Homework(posn);
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

  private Map<Character, String> fullName;

  private void loadKey() throws FileNotFoundException {
    fullName = new HashMap<>();
    System.out.println("loading");
    InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("misc/keyToName.txt");
    if (inputStream == null) throw new LevelNotLoadedException("Error loading key.");
    Scanner scanner = new Scanner(inputStream);
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      line = line.replace(" ", "");
      String[] splitted = line.split(",");
      fullName.put(splitted[0].charAt(0), splitted[1]);
    }
    scanner.close();
  }

  /**
   * Generates a specified level
   * @param level the level to generate (positive integer)
   */
  public void init(int level) {
    InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("UNCLevel_" + level);
    if (inputStream == null) throw new LevelNotLoadedException("Error loading level");
    Scanner scanner = new Scanner(inputStream);
    System.out.println("Loading level " + level);
    height = scanner.nextInt();
    width = scanner.nextInt();
    enemyList.clear();

    // to build map: type = 1, full name; type = 2, capital letter representation
    int load_type = scanner.nextInt();
    board = new Piece[height][width];
    boolean playerFound = false;
    scanner.nextLine();
    String line;
    if (load_type == 1) {
      for (int i = 0; i < height; i++) {
        line = scanner.nextLine().strip().replace(" ", "");
        if (line.charAt(line.length() - 1) == ',') line = line.substring(0, line.length() - 1);
        String[] splitted = line.split(",");
        for (int j = 0; j < width; j++) {
          board[i][j] = nameToPiece(splitted[j], new Posn(i, j));
          if (splitted[j].equals("player")) {
            if (playerFound) throw new PlayerAlreadyFoundException("board cannot have two players");
            playerFound = true;
            player = (Player) board[i][j];
          } else if (splitted[j].equals("enemy")) {
            enemyList.add((Enemy)board[i][j]);
          }
        }
      }
    } else {
      System.out.println(fullName);
      for (int i = 0; i < height; i++) {
        line = scanner.nextLine().toLowerCase();
        for (int j = 0; j < width; j++) {
          String name = fullName.get(line.charAt(j));
          board[i][j] = nameToPiece(name, new Posn(i, j));
          if (name.equals("player")) {
            if (playerFound) throw new PlayerAlreadyFoundException("board cannot have two players");
            playerFound = true;
            player = (Player) board[i][j];
          } else if (name.equals("enemy")) {
            enemyList.add((Enemy)board[i][j]);
          }
        }
      }
    }
    scanner.close();
  }

  /**
   * Gives the width or number of columns of the grid
   * @return
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gives the height or number of rows of the grid
   * @return
   */
  public int getHeight() {
    return height;
  }

  /**
   * Accesses the Piece at the specified position on the board
   * @param posn
   * @return
   */
  public Piece getPiece(Posn posn) {
    int r = posn.getRow();
    int c = posn.getCol();
    if (r < 0 || r >= height || c < 0 || c >= width) throw new PieceOutOfBoundsException("");
    return board[posn.getRow()][posn.getCol()];
  }

  private CollisionEvent moveMovable(MovablePiece movable, int drow, int dcol) {
    int r = movable.getPosn().getRow();
    int c = movable.getPosn().getCol();
    int nr = r + drow;
    int nc = c + dcol;

    // out of bounds
    if (nr < 0 || nr >= height || nc < 0 || nc >= width) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);;
    CollisionEvent collisionEvent = movable.collide(getPiece(new Posn(nr, nc)));

    // handle collision types
    CollisionEvent.Result res = collisionEvent.getResult();
    boolean updatePosition = true;
    CollisionEvent.Result[] ignore = {CollisionEvent.Result.NEXT_LEVEL, CollisionEvent.Result.BLOCK, CollisionEvent.Result.GAME_OVER};
    for (CollisionEvent.Result ig : ignore) {
      if (res == ig) updatePosition = false;
    }
    if (updatePosition) movable.move(drow, dcol);
    return collisionEvent;
  }

  public CollisionEvent movePlayer(int drow, int dcol) {
    return moveMovable(player, drow, dcol);
  }

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
          sb.append(piece.toString().charAt(0));
        }

        sb.append(' ');
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}

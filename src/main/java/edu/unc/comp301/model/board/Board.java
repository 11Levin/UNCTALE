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
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Board {
    // board info
    private Piece[][] board;
    private int width;
    private int height;
    private final int viewRadius = 4;
    private final Random random;
    private final String[] enemyNames = {"student", "professor"};

    // entity info
    private Player player;
    private ArrayList<Enemy> enemyList = new ArrayList<>();

    /**
     * Initializes an empty board with size (height, width)
     *
     * @param width  number of columns
     * @param height number of rows
     */
    public Board(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        board = new Piece[height][width];
        random = new Random();
        try {
            loadKey();
        } catch (FileNotFoundException e) {
            throw new LevelNotLoadedException("invalid key to load map");
        }
    }

    public Board(Piece[][] board) {
        this.board = board;
        random = new Random();
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
     * gives a Piece object from a string representing the piece.
     * Ex: "player" will become a PlayerImpl object.
     *
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
     *
     * @param level the level to generate (positive integer)
     */
    public void init(int level) {
        InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("level/UNCLevel_" + level);
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
                    } else {
                        for (String enemyName : enemyNames) {
                          if (enemyName.equals(splitted[j])) enemyList.add((Enemy) board[i][j]);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < height; i++) {
                line = scanner.nextLine().toLowerCase();
                for (int j = 0; j < width; j++) {
                    String name = fullName.get(line.charAt(j));
                    board[i][j] = nameToPiece(name, new Posn(i, j));
                    if (name.equals("player")) {
                        if (playerFound) throw new PlayerAlreadyFoundException("board cannot have two players");
                        playerFound = true;
                        player = (Player) board[i][j];
                    } else {
                      for (String enemyName : enemyNames) {
                        if (enemyName.equals(name)) enemyList.add((Enemy) board[i][j]);
                      }
                    }
                }
            }
        }
        scanner.close();
    }

    /**
     * Gives the width or number of columns of the grid
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gives the height or number of rows of the grid
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Accesses the Piece at the specified position on the board
     *
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
        if (nr < 0 || nr >= height || nc < 0 || nc >= width)
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
        CollisionEvent collisionEvent = movable.collide(getPiece(new Posn(nr, nc)));

        // handle collision types
        CollisionEvent.Result res = collisionEvent.getResult();
        boolean updatePosition = true;
        CollisionEvent.Result[] ignore = {CollisionEvent.Result.NEXT_LEVEL, CollisionEvent.Result.BLOCK, CollisionEvent.Result.GAME_OVER};
        for (CollisionEvent.Result ig : ignore) {
            if (res == ig) updatePosition = false;
        }
        if (updatePosition) {
          movable.move(drow, dcol);
          board[r][c] = null;
          board[nr][nc] = movable;
        }
        return collisionEvent;
    }

    public CollisionEvent movePlayer(int drow, int dcol) {
        return moveMovable(player, drow, dcol);
    }

    public void moveEnemies() {
        for (Enemy enemy : enemyList) {
          int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
          int idx = Math.abs(random.nextInt()) % 4;
          moveMovable(enemy, dir[idx][0], dir[idx][1]);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece == null) {
                    sb.append(" ");
                } else {
                    sb.append(piece.toString().charAt(0));
                }

                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of grid components that need to be drawn
     *
     * @return
     */
    public String[][] getToDraw() {
        String[][] rtn = new String[2 * viewRadius + 1][2 * viewRadius + 1];
        int r = player.getPosn().getRow();
        int c = player.getPosn().getCol();
        for (int i = r - viewRadius; i <= r + viewRadius; i++) {
            for (int j = c - viewRadius; j <= c + viewRadius; j++) {
                // visual piece row and column wrt player
                int visualR = i - r + viewRadius;
                int visualC = j - c + viewRadius;

                String toAdd = "immovable/UNCWall.png";

                // Check piece at (i, j) is within board bounds
                if (i >= 0 && i < getHeight() && j >= 0 && j < getWidth()) {
                    Piece piece = getPiece(new Posn(i, j));
                    toAdd = "immovable/UNCGrass.png";
                    if (piece != null) {
                        toAdd = piece.getResourcePath();
                    }
                }
                rtn[visualR][visualC] = toAdd;
            }
        }
        return rtn;
    }
}

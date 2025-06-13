package edu.unc.comp301.model.board;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.model.exception.LevelNotLoadedException;
import edu.unc.comp301.model.exception.PieceOutOfBoundsException;
import edu.unc.comp301.model.exception.PlayerAlreadyFoundException;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.PlayerStats;
import edu.unc.comp301.model.piece.buff.Apple;
import edu.unc.comp301.model.piece.buff.Homework;
import edu.unc.comp301.model.piece.buff.Pencil;
import edu.unc.comp301.model.piece.immovable.Exit;
import edu.unc.comp301.model.piece.immovable.Obstacle;
import edu.unc.comp301.model.piece.immovable.Treasure;
import edu.unc.comp301.model.piece.movable.*;

import java.io.InputStream;
import java.util.*;

public class Board extends GameElement {
    // board info
    private Piece[][] board;
    private int width;
    private int height;

    // entity info
    private Player player;
    private final ArrayList<Enemy> enemyList = new ArrayList<>();

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
    }

    public Board(Piece[][] board) {
        this.board = board;
        width = board[0].length;
        height = board.length;
    }

    public Board() {}

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
            case "obstacle" -> new Obstacle(posn);
            case "enemy" -> new Student(posn, 100);
            case "student" -> new Student(posn, 100);
            case "professor" -> new Professor(posn, 120);
            case "treasure" -> new Treasure(posn);
            case "exit" -> new Exit(posn);
            case "apple" -> new Apple(posn);
            case "pencil" -> new Pencil(posn);
            case "homework" -> new Homework(posn);
            default -> null;
        };
    }


    /**
     * Generates a specified level
     *
     * @param level the level to generate (positive integer)
     * @param playerStats the player's attack and health
     */
    public void init(int level, PlayerStats playerStats) {
        InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("level/UNCLevel_" + level);
        if (inputStream == null) throw new LevelNotLoadedException("Error loading level");
        Scanner scanner = new Scanner(inputStream);
        System.out.println("Loading level " + level);
        height = scanner.nextInt();
        width = scanner.nextInt();
        enemyList.clear();

        board = new Piece[height][width];
        boolean playerFound = false;
        scanner.nextLine();
        String line;
        for (int i = 0; i < height; i++) {
            line = scanner.nextLine().toLowerCase();
            for (int j = 0; j < width; j++) {
                String name = fullName(line.charAt(j));
                if (name.equals("player")) {
                  if (playerFound) throw new PlayerAlreadyFoundException("board cannot have two players");
                  playerFound = true;
                  board[i][j] = new Player(new Posn(i, j), playerStats.getHealth(), playerStats.getAttack());
                  player = (Player) board[i][j];
                  continue;
                }
                board[i][j] = nameToPiece(name, new Posn(i, j));
                for (String enemyName : enemyNames) {
                  if (enemyName.equals(name)) enemyList.add((Enemy) board[i][j]);
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
        Posn nPos = new Posn(nr, nc);

        // out of bounds
        if (nr < 0 || nr >= height || nc < 0 || nc >= width)
            return CollisionEvent.BLOCK;
        CollisionEvent collisionEvent = movable.collide(getPiece(nPos));

        // update player
        player.setHealth(player.getHealth() + collisionEvent.getHealth());
        player.setAttack(player.getAttack() + collisionEvent.getAttack());

        // handle collision types
        CollisionEvent.Result res = collisionEvent.getResult();
        boolean updatePosition = true;
        CollisionEvent.Result[] ignore = {CollisionEvent.Result.NEXT_LEVEL, CollisionEvent.Result.BLOCK, CollisionEvent.Result.GAME_OVER};
        if (res == CollisionEvent.Result.ATTACK) {
            Enemy enemy = (Enemy)getPiece(nPos);
            player.attack(player.getAttack(), enemy);
            System.out.println("Enemy Hurt! Health is at: " + enemy.getHealth());
            if (enemy.getHealth() == 0) { // enemy will drop buff when killed
                board[nr][nc] = nameToPiece("pencil", new Posn(nr, nc));
                enemyList.remove(enemy);
            }
            return CollisionEvent.BLOCK;
        }

        // update position if no obstacle or enemy is in the way
        if (res == CollisionEvent.Result.PICK_ITEM) {
            player.setAttack(player.getAttack() + collisionEvent.getAttack());
            player.setHealth(player.getHealth() + collisionEvent.getHealth());
        }

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
     * @return
     */
    public String[] getToDraw() {
        String[] rtn = new String[2 * viewRadius + 1];
        int r = player.getPosn().getRow();
        int c = player.getPosn().getCol();
        for (int i = r - viewRadius; i <= r + viewRadius; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = c - viewRadius; j <= c + viewRadius; j++) {
                Character toAdd = 'w';

                // Check piece at (i, j) is within board bounds
                if (i >= 0 && i < getHeight() && j >= 0 && j < getWidth()) {
                    Piece piece = getPiece(new Posn(i, j));
                    toAdd = 'g';
                    if (piece != null) {
                        toAdd = shortName(piece.getName());
                    }
                }
                sb.append(toAdd);
            }
            rtn[i + viewRadius - r] = sb.toString();
        }
        return rtn;
    }

  public PlayerStats getPlayerStats() {
      return new PlayerStats(player.getAttack(), player.getHealth());
    }
}

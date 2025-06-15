package edu.unc.comp301.model.board;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.model.EnemyFactory;
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
import javafx.application.Platform;

import java.io.InputStream;
import java.util.*;

public class Board extends GameElement {
    // board info
    private Piece[][] board;
    private int width;
    private int height;
    private volatile int level = 0;
    private ArrayList<Thread> threadList = new ArrayList<>();
    private ArrayList<Bullet> bulletList = new ArrayList<>();

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
            case "enemy" -> new Student(posn);
            case "student" -> EnemyFactory.createEnemy(name, posn);
            case "professor" -> EnemyFactory.createEnemy(name, posn);
            case "ramses" -> EnemyFactory.createEnemy(name, posn);
            case "bullet" -> EnemyFactory.createBullet(posn,10,new int[] {0,0},1);
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
    public synchronized void init(int level, PlayerStats playerStats) {
        clearThreads();
        clearEntities();
        InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("level/UNCLevel_" + level);
        if (inputStream == null) throw new LevelNotLoadedException("Error loading level");
        Scanner scanner = new Scanner(inputStream);
        System.out.println("Loading level " + level);
        height = scanner.nextInt();
        width = scanner.nextInt();


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
        loadEnemies();
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


        if (nr < 0 || nr >= height || nc < 0 || nc >= width){
            if(movable.getName().equals("bullet")){
                Bullet enemy = (Bullet) getPiece(new Posn(r,c));
                player.attack(100, enemy);
                board[r][c] = null;
                bulletList.remove(enemy);
            }
            return CollisionEvent.BLOCK;
        }

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
                if(enemy.getName().equals("student")){
                    board[nr][nc] = nameToPiece("apple", new Posn(nr, nc));
                }
                if(enemy.getName().equals("professor")){
                    board[nr][nc] = nameToPiece("pencil", new Posn(nr, nc));
                }
                if(enemy.getName().equals("ramses")){
                    board[nr][nc] = nameToPiece("exit", new Posn(nr, nc));
                }

                enemyList.remove(enemy);
            }
            return CollisionEvent.BLOCK;
        }

        if (res == CollisionEvent.Result.HIT) {
            Enemy enemy;
            if(movable.getName().equals("player")){
                enemy = (Bullet)getPiece(nPos);
                player.attack(100, enemy);
                player.setHealth(player.getHealth()-enemy.getAttack());
                System.out.println("Player Hurt! Health is at: " + player.getHealth());
                board[nr][nc] = null;
                bulletList.remove(enemy);
            } else {
                enemy = (Bullet)getPiece(new Posn(r,c));
                player.attack(100, enemy);
                player.setHealth(player.getHealth()-enemy.getAttack());
                System.out.println("Player Hurt! Health is at: " + player.getHealth());
                board[r][c] = null;
                bulletList.remove(enemy);
                return CollisionEvent.BLOCK;
            }
        }

        if((movable.getName().equals("bullet")) && (!res.equals(CollisionEvent.Result.CONTINUE))){
            Enemy enemy = (Enemy)getPiece(new Posn(r,c));
            player.attack(100, enemy);
            board[r][c] = null;
            bulletList.remove(enemy);
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

    public void bullet(Enemy enemy, int pattern){
        int r = enemy.getPosn().getRow();
        int c = enemy.getPosn().getCol();
        ArrayList<int[]> dir = new ArrayList<>();
        if ((r-1) >= 0){
            if (board[r-1][c] == null || board[r-1][c].equals(player)){
                dir.add(new int[] {-1, 0});
            }
        }
        if ((r+1) < height){
            if (board[r+1][c] == null || board[r+1][c].equals(player)){
                dir.add(new int[] {1, 0});
            }
        }
        if ((c-1) >= 0){
            if (board[r][c-1] == null || board[r][c-1].equals(player)){
                dir.add(new int[] {0, -1});
            }
        }
        if ((c+1) < width){
            if (board[r][c+1] == null || board[r][c+1].equals(player)){
                dir.add(new int[] {0, 1});
            }
        }
        for (int i = 0; i < pattern; i++){
            int idx = 1;
            if ((dir.isEmpty())){
                break;
            }
            idx = Math.abs(random.nextInt()) % (dir.size());
            Enemy bullet = EnemyFactory.createBullet(
                    new Posn(r+dir.get(idx)[0], c+dir.get(idx)[1]),
                    enemy.getAttack(),
                    dir.get(idx),
                    enemy.getSpeed());
            bulletList.add((Bullet) bullet);
            if (board[r+dir.get(idx)[0]][c+dir.get(idx)[1]] == null){
                board[r+dir.get(idx)[0]][c+dir.get(idx)[1]] = bullet;
                moveBullet((Bullet) bullet);
            } else{
                player.setHealth(player.getHealth()-enemy.getAttack());
                bulletList.remove(bullet);
            }





            dir.remove(idx);
        }


    }

    public void moveBullet(Bullet bullet){
        int cur_level = level;
        Thread moveBullet = new Thread(() -> {
            try {
                Thread.sleep(3000/bullet.getSpeed());
            } catch (InterruptedException e) {

            }
            while ((bullet.getHealth() > 0) && (this.level == cur_level)) {
                moveMovable(bullet,bullet.getDirection()[0], bullet.getDirection()[1]);
                try {
                    Thread.sleep(3000/bullet.getSpeed());
                } catch (InterruptedException e) {

                }

            }


        });
        moveBullet.start();
        threadList.add(moveBullet);
    }

    public void spawnBullet(int tick) {
        //System.out.println(tick);
        for (Enemy enemy: enemyList){
            int pattern = 0;
            if ((enemy.getName().equals("student"))&& tick % 6 == 0){
                pattern = 1;
                bullet(enemy,pattern);
            }
            if ((enemy.getName().equals("professor")) && tick % 3 == 0){
                pattern = 2;
                bullet(enemy,pattern);
            }
            if ((enemy.getName().equals("ramses"))&& tick % 2 == 0){
                pattern = 4;
                bullet(enemy,pattern);
            }

        }

    }

    public void moveEnemy(Enemy enemy) {
        ArrayList<int[]> dir = new ArrayList<>();
        dir.add(new int[] {-1, 0});
        dir.add(new int[] {1, 0});
        dir.add(new int[] {0, -1});
        dir.add(new int[] {0, 1});
        for (int i = 0; i < 4; i++){
            int idx = Math.abs(random.nextInt()) % (dir.size());
            CollisionEvent res = moveMovable(enemy, dir.get(idx)[0], dir.get(idx)[1]);
            if ((res.getResult().equals(CollisionEvent.Result.CONTINUE))||(res.getResult().equals(CollisionEvent.Result.HIT))){
                break;
            }
            dir.remove(idx);
        }
    }

    public void loadEnemies(){
        for (Enemy enemy: enemyList){
            if (!enemy.getName().equals("ramses")){
                enemyMove(enemy);
            }
        }
    }

    public void enemyMove(Enemy enemy){
        int cur_level = level;
        Thread enemyMove = new Thread(() -> {
            while ((enemy.getHealth() > 0) && (this.level == cur_level)) {
                moveEnemy(enemy);
                try {
                    Thread.sleep(3000/enemy.getSpeed());
                } catch (InterruptedException e) {
                }
            }
        });
        enemyMove.start();
        threadList.add(enemyMove);
    }

    public synchronized void clearThreads(){
        for (Thread thread : threadList){
            thread.interrupt();
        }
        threadList.clear();
    }

    public synchronized void clearEntities(){
        for(Enemy enemy: enemyList){
            enemy.setHealth(0);
            board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        }
        for(Enemy bullet: bulletList){
            bullet.setHealth(0);
            board[bullet.getPosn().getRow()][bullet.getPosn().getCol()] = null;
        }
        enemyList.clear();
        bulletList.clear();
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

package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Piece;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.*;

public class GameView implements FXComponent{
    private final BoardImpl board;
    private final int tileSize = 64;
    Map<String, Image> images = new HashMap<>();
    private GridPane gridPane;
    private GameBar gameBar;
    Map<String, Parent>[][] rendered;
    int viewRadius = 4;
    private String[] toLoad = {"UNCApple_Buff.png", "UNCGrass.png", "UNCHomework_Debuff.jpg", "UNCLogo_Exit.png", "UNCMoney_Treasure.png",
            "UNCPencil_Buff.png", "UNCPlayer.png", "UNCPortal.png", "UNCProfessor_Enemy.png", "UNCRam_Obstacle.png", "UNCRamses_Enemy.png",
            "UNCStudent_Enemy.png", "UNCTile_1.jpg", "UNCTile_2.jpg", "UNCTile_3.jpg", "UNCWall.png", "UNCUnloaded.png"};
    Parent nodes[][];

    public GameView(BoardImpl board) {
        this.board = board;
        loadImages();
        board.setGameView(this);
    }

    private void loadImages() {
        for (String path : toLoad) {
            images.put(path, new Image(path));
        }
    }

    private ImageView getView(String path) {
        ImageView rtn = new ImageView(images.get(path));
        rtn.setFitWidth(tileSize);
        rtn.setFitHeight(tileSize);
        return rtn;
    }

    /**
     * Do not put in loop
     * @return
     */
    @Override
    public Parent render() {
        VBox vBox = new VBox();
        gameBar = new GameBar();
        gridPane = new GridPane();
        rendered = new HashMap[2 * viewRadius + 1][2 * viewRadius + 1];
        nodes = new Parent[2 * viewRadius + 1][2 * viewRadius + 1];

        // create all tiles beforehand
        for (int i = 0; i < 2 * viewRadius + 1; i++) {
            for (int j = 0; j < 2 * viewRadius + 1; j++) {
                rendered[i][j] = new HashMap<>();
                for (String tile : toLoad) {
                    StackPane stackPane = new StackPane();
                    if (tile.equals("UNCUnloaded.png")) {
                        stackPane.getChildren().add(getView("UNCUnloaded.png"));
                        rendered[i][j].put(tile, stackPane);
                        continue;
                    }
                    stackPane.getChildren().add(getView("UNCGrass.png"));
                    if (!tile.equals("UNCGrass.png")) {
                        stackPane.getChildren().add(getView(tile));
                    }
                    rendered[i][j].put(tile, stackPane);
                }
            }
        }
        vBox.getChildren().addAll(gameBar.render(), gridPane);
        gameBar.updateStats(board.getHealth(), board.getPoints(), board.getLevel());
        update(new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE));
        return vBox;
    }


    /**
     * Call to update visuals
     * @param collisionEvent
     */
    public void update(CollisionEvent collisionEvent) {
        Posn posn = board.getPlayer().getPosn();
        int r = posn.getRow();
        int c = posn.getCol();
//        System.out.println("Board dims: " + board.getHeight() + ", " + board.getWidth());
        for (int i = r - viewRadius; i <= r + viewRadius; i++) {
            for (int j = c - viewRadius; j <= c + viewRadius; j++) {
                // visual piece row and column wrt player
                int visualR = i - r + viewRadius;
                int visualC = j - c + viewRadius;

                Parent toAdd = rendered[visualR][visualC].get("UNCWall.png");
                if (nodes[visualC][visualR] != null) gridPane.getChildren().remove(nodes[visualR][visualC]);

                // Check piece at (i, j) is within board bounds
                if (i >= 0 && i < board.getHeight() && j >= 0 && j < board.getWidth()) {
                    Piece piece = board.getPiece(new Posn(i, j));
                    toAdd = rendered[visualR][visualC].get("UNCGrass.png");
                    if (piece != null) {
                        toAdd = rendered[visualR][visualC].get(piece.getResourcePath());
                    }
                }
                nodes[visualR][visualC] = toAdd;
                gridPane.add(toAdd, visualC, visualR);
            }
        }

        // update game stats bar
        if (gameBar != null) {
            if (collisionEvent.getHealth() != 0) {
                gameBar.setHealth(board.getHealth());
            }
            if (collisionEvent.getPoints() != 0) {
                gameBar.setScore(board.getPoints());
            }
            if (collisionEvent.getResults().equals(CollisionEvent.Result.NEXT_LEVEL)) {
                gameBar.setLevel(board.getLevel());
            }
        }


    }

    public void update(CollisionEvent collisionEvent, Posn posn) {
        int r = posn.getRow();
        int c = posn.getCol();
//        System.out.println("Board dims: " + board.getHeight() + ", " + board.getWidth());
        for (int i = r - viewRadius; i <= r + viewRadius; i++) {
            for (int j = c - viewRadius; j <= c + viewRadius; j++) {
                // visual piece row and column wrt player
                int visualR = i - r + viewRadius;
                int visualC = j - c + viewRadius;

                Parent toAdd = rendered[visualR][visualC].get("UNCWall.png");
                if (nodes[visualC][visualR] != null) gridPane.getChildren().remove(nodes[visualR][visualC]);

                // Check piece at (i, j) is within board bounds
                if (i >= 0 && i < board.getHeight() && j >= 0 && j < board.getWidth()) {
                    Piece piece = board.getPiece(new Posn(i, j));
                    toAdd = rendered[visualR][visualC].get("UNCGrass.png");
                    if (piece != null) {
                        toAdd = rendered[visualR][visualC].get(piece.getResourcePath());
                    }
                }
                nodes[visualR][visualC] = toAdd;
                gridPane.add(toAdd, visualC, visualR);
            }
        }

        // update game stats bar
        if (gameBar != null) {
            if (collisionEvent.getHealth() != 0) {
                gameBar.setHealth(board.getHealth());
            }
            if (collisionEvent.getPoints() != 0) {
                gameBar.setScore(board.getPoints());
            }
            if (collisionEvent.getResults().equals(CollisionEvent.Result.NEXT_LEVEL)) {
                gameBar.setLevel(board.getLevel());
            }
        }


    }
}

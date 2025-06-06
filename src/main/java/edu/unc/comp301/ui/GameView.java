package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Piece;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
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

    public GameView(BoardImpl board) {
        this.board = board;
        loadImages();
        board.setGameView(this);
    }

    private void loadImages() {
        String[] toLoad = {"UNCApple_Buff.png", "UNCGrass.png", "UNCHomework_Debuff.jpg", "UNCLogo_Exit.png", "UNCMoney_Treasure.png",
        "UNCPencil_Buff.png", "UNCPlayer.png", "UNCPortal.png", "UNCProfessor_Enemy.png", "UNCRam_Obstacle.png", "UNCRamses_Enemy.png",
        "UNCStudent_Enemy.png", "UNCTile_1.jpg", "UNCTile_2.jpg", "UNCTile_3.jpg"};
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

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        gameBar = new GameBar();
        gridPane = new GridPane();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(getView("UNCGrass.png"));
                Piece piece = board.getPiece(new Posn(i, j));
                if (piece != null) {
                    if (piece.toString().equals("treasure")) {
                        System.out.println("Spawned treasure!");
                    }
                    stackPane.getChildren().add(getView(piece.getResourcePath()));
                }
                gridPane.add(stackPane, j, i);
            }
        }
        vBox.getChildren().addAll(gameBar.render(), gridPane);
        gameBar.updateStats(board.getHealth(), board.getPoints(), board.getLevel());
        return vBox;
    }

    public void update(CollisionEvent collisionEvent) {
        for (Posn posn : collisionEvent.getToUpdate()) {
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(getView("UNCGrass.png"));
            Piece piece = board.getPiece(posn);
            if (piece != null) {
                stackPane.getChildren().add(getView(piece.getResourcePath()));
            }
            gridPane.add(stackPane, posn.getCol(), posn.getRow());
        }
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

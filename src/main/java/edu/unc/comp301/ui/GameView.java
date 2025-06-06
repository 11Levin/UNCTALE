package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.Piece;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class GameView implements FXComponent{
    private final BoardImpl board;
    private final int tileSize = 50;
    Map<String, Image> images = new HashMap<>();
    private GridPane gridPane;

    public GameView(BoardImpl board) {
        this.board = board;
        loadImages();
        board.setGameView(this);
    }

    private void loadImages() {
        String[] toLoad = {"UNCApple_Buff.png", "UNCGrass.jpg", "UNCHomework_Debuff.jpg", "UNCLogo_Exit.png", "UNCMoney_Treasure.png",
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
        gridPane = new GridPane();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(getView("UNCGrass.jpg"));
                Piece piece = board.getPiece(new Posn(i, j));
                if (piece != null) {
                    stackPane.getChildren().add(getView(piece.getResourcePath()));
                }
                gridPane.add(stackPane, j, i);
            }
        }
        return gridPane;
    }

    public void update(List<Posn> toUpdate) {
        for (Posn posn : toUpdate) {
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(getView("UNCGrass.jpg"));
            Piece piece = board.getPiece(posn);
            if (piece != null) {
                stackPane.getChildren().add(getView(piece.getResourcePath()));
            }
            gridPane.add(stackPane, posn.getCol(), posn.getRow());
        }
    }
}

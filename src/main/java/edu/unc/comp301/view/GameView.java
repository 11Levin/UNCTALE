package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.GameStats;
import edu.unc.comp301.model.piece.Piece;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class GameView extends View {
    // visual constants
    private final int tileSize = 64;
    private final int viewRadius = 4;
    private boolean isRendered = false;

    Map<String, Image> images = new HashMap<>();
    private final GameBar gameBar;
    private final VBox vBox;

    // grid of pre-rendered stackpanes for each tile in the view area to reduce re-rendering
    private final Map<String, Parent>[][] rendered;

    // resource paths of all images
    private final String[] toLoad = {"buff/UNCApple_Buff.png", "immovable/UNCGrass.png", "buff/UNCHomework_Debuff.jpg", "immovable/UNCLogo_Exit.png", "immovable/UNCMoney_Treasure.png",
            "buff/UNCPencil_Buff.png", "movable/UNCPlayer.png", "immovable/UNCPortal.png", "movable/UNCProfessor_Enemy.png", "immovable/UNCRam_Obstacle.png", "movable/UNCRamses_Enemy.png",
            "movable/UNCStudent_Enemy.png", "immovable/UNCTile_1.jpg", "immovable/UNCTile_2.jpg", "immovable/UNCTile_3.jpg", "immovable/UNCWall.png", "immovable/UNCUnloaded.png"};

    public GameView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameBar = new GameBar();
        vBox = new VBox();
        rendered = new HashMap[2 * viewRadius + 1][2 * viewRadius + 1];
        loadImages();
        render();
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

    @Override
    public Parent render() {
        if (isRendered) return vBox;
        isRendered = true;

        // create all tiles beforehand
        for (int i = 0; i < 2 * viewRadius + 1; i++) {
            for (int j = 0; j < 2 * viewRadius + 1; j++) {
                rendered[i][j] = new HashMap<>();
                for (String tile : toLoad) {
                    StackPane stackPane = new StackPane();
                    if (tile.equals("immovable/UNCUnloaded.png")) {
                        stackPane.getChildren().add(getView("immovable/UNCUnloaded.png"));
                        rendered[i][j].put(tile, stackPane);
                        continue;
                    }
                    stackPane.getChildren().add(getView("immovable/UNCGrass.png"));
                    if (!tile.equals("immovable/UNCGrass.png")) {
                        stackPane.getChildren().add(getView(tile));
                    }
                    rendered[i][j].put(tile, stackPane);
                }
            }
        }
        vBox.getChildren().addAll(gameBar.render(), new GridPane());
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_CONTINUE) return;

        // update grid
        GridPane gridPane = new GridPane();
        String[][] toDraw = model.getToDraw();
        for (int i = 0; i < 2 * viewRadius + 1; i++) {
            for (int j = 0; j < 2 * viewRadius + 1; j++) {
                Parent toAdd = rendered[i][j].get(toDraw[i][j]);
                gridPane.add(toAdd, j, i);
            }
        }
        vBox.getChildren().removeLast();
        vBox.getChildren().add(gridPane);

        // update game bar
        gameBar.updateStats(gameStats);

        stage.getScene().setRoot(render());
    }
}

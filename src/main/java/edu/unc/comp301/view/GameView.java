package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class GameView extends View {
    private boolean isRendered = false;

    private final GameBar gameBar;
    private final VBox vBox;

    // grid of pre-rendered stackpanes for each tile in the view area to reduce re-rendering
    private final Map<Character, Parent>[][] rendered;


    public GameView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameBar = new GameBar();
        vBox = new VBox();
        rendered = new HashMap[2 * viewRadius + 1][2 * viewRadius + 1];
        render();
    }

    private ImageView getView(Character c) {
        ImageView imageView = new ImageView(shortToImage(c));
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);

        return imageView;
    }

    private ImageView getView(String s) {
        return getView(shortName(s));
    }

    @Override
    public Parent render() {
        if (isRendered) return vBox;
        isRendered = true;

        // create all tiles beforehand
        for (int i = 0; i < 2 * viewRadius + 1; i++) {
            for (int j = 0; j < 2 * viewRadius + 1; j++) {
                rendered[i][j] = new HashMap<>();
                for (Character c : getToLoad()) {
                    StackPane stackPane = new StackPane();
                    if (c == 'u') {
                        stackPane.getChildren().add(getView(c));
                        rendered[i][j].put(c, stackPane);
                        continue;
                    }
                    stackPane.getChildren().add(getView('g'));
                    if (c != 'g') {
                        stackPane.getChildren().add(getView(c));
                    }
                    rendered[i][j].put(c, stackPane);
                }
            }
        }
        StackPane grid = new StackPane();
        grid.getChildren().addAll(new GridPane(), new GridPane());
        vBox.getChildren().addAll(gameBar.render(), grid);
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_CONTINUE) return;

        // update grid
        GridPane gridPane = new GridPane();
        String[] toDraw = model.getToDraw();
        for (int i = 0; i < 2 * viewRadius + 1; i++) {
            for (int j = 0; j < 2 * viewRadius + 1; j++) {
                Parent toAdd = rendered[i][j].get(toDraw[i].charAt(j));
                gridPane.add(toAdd, j, i);
            }
        }

        // update buffs
        Canvas buffs = new Canvas((2 * viewRadius + 1) * tileSize, (2 * viewRadius + 1) * tileSize);
        GraphicsContext gc = buffs.getGraphicsContext2D();
        for (int i = 0; i < MAX_NUMBER_BUFFS; i++) {
            String buff = toDraw[i + 2 * viewRadius + 1];
            if (buff != null) {
                gc.drawImage(fullToImage(buff), viewRadius * tileSize, viewRadius * tileSize, tileSize, tileSize);
            }
        }
        vBox.getChildren().removeLast();
        StackPane grid = new StackPane();
        grid.getChildren().addAll(gridPane, buffs);
        vBox.getChildren().add(grid);

        // update game bar
        gameBar.updateStats(gameStats);
        stage.getScene().setRoot(render());
    }
}

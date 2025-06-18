package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class GameView extends View {
    private final GameBar gameBar;
    private final VBox vBox;

    // grid of pre-rendered stackpanes for each tile in the view area to reduce re-rendering
    private Map<Character, Parent>[][] rendered;




    public GameView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameBar = new GameBar(controller);
        vBox = new VBox();
        vBox.getStylesheets().add("main.css");
        render();
    }

    private ImageView getView(Character c) {
        ImageView imageView = new ImageView(shortToImage(c));
        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);

        return imageView;
    }

    private ImageView getView(String s) {
        return getView(shortName(s));
    }

    public Parent render(int verticalRadius, int horizontalRadius) {
        vBox.getChildren().clear();
        rendered = new HashMap[2 * verticalRadius + 1][2 * horizontalRadius + 1];

        // create all tiles beforehand
        for (int i = 0; i < 2 * verticalRadius + 1; i++) {
            for (int j = 0; j < 2 * horizontalRadius + 1; j++) {
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
        vBox.getChildren().addAll(gameBar.render(), new GridPane());
        return vBox;
    }

    @Override
    public Parent render() {
        return render(getVerticalRadius(), getHorizontalRadius());
    }


    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_CONTINUE) return;

        int verticalRadius = getVerticalRadius();
        int horizontalRadius = getHorizontalRadius();
        if (2 * verticalRadius + 1 != rendered.length || 2 * horizontalRadius + 1 != rendered[0].length) {
            render(verticalRadius, horizontalRadius);
        };

        // update grid
        GridPane gridPane = new GridPane();
        String[] toDraw = model.getToDraw(verticalRadius, horizontalRadius);
        for (int i = 0; i < 2 * verticalRadius + 1; i++) {
            for (int j = 0; j < 2 * horizontalRadius + 1; j++) {
                Parent toAdd = rendered[i][j].get(toDraw[i].charAt(j));
                gridPane.add(toAdd, j, i);
            }
        }

        // draw player and buffs
        StackPane center = new StackPane();
        center.getChildren().addAll(getView('g'), getView('p'));
        for (String buff : gameStats.getBuffs()) center.getChildren().add(getView(buff));
        gridPane.add(center, horizontalRadius, verticalRadius);

        vBox.getChildren().clear();
        gameBar.updateStats(gameStats);
        vBox.getChildren().addAll(gameBar.render(), gridPane);
        stage.getScene().setRoot(vBox);
    }
}

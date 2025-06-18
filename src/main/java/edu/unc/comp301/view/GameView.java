package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.Key;
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
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);

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
        vBox.getChildren().addAll(new GridPane());
        return vBox;
    }

    @Override
    public Parent render() {
        return render(getVerticalRadius(), getHorizontalRadius());
    }


    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_CONTINUE) return;

        // prevent updates happening mid-code
        int verticalRadius = getVerticalRadius();
        int horizontalRadius = getHorizontalRadius();
        System.out.println(verticalRadius + " " + horizontalRadius);
        if (2 * verticalRadius + 1 != rendered.length || 2 * horizontalRadius + 1 != rendered[0].length) {
            render(verticalRadius, horizontalRadius);
        };

//        System.out.println("updating");
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
        for (int i = 0; i < MAX_NUMBER_BUFFS; i++) {
//            System.out.println("drawing buffs: ");
//            System.out.println(toDraw[2 * viewRadius + 1 + i]);
            if (toDraw[2 * verticalRadius + 1 + i] != null) {
                center.getChildren().add(getView(toDraw[2 * verticalRadius + 1 + i]));
            }
        }
        gridPane.add(center, horizontalRadius, verticalRadius);

        vBox.getChildren().clear();
        vBox.getChildren().add(gridPane);

        // update game bar
        gameBar.updateStats(gameStats);
        stage.getScene().setRoot(vBox);
    }
}

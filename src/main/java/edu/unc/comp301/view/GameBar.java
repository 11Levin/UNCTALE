package edu.unc.comp301.view;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.piece.GameStats;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class GameBar extends GameElement implements FXComponent {
    private final Label scoreNumber = new Label("0");
    private final Label levelNumber = new Label("1");
    private final HBox healthBar = new HBox();
    private final HBox attackBar = new HBox();
    private final Map<String, Image> images = new HashMap<>();
    private final Controller controller;
    private final Button right = new Button("D");
    private final Button up = new Button("w");
    private final Button left = new Button("A");
    private final Button down = new Button("S");

    public GameBar(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Parent render() {
        loadImages();
        HBox bar = new HBox();
        bar.setPrefWidth((2 * getHorizontalRadius() + 1) * TILE_SIZE);
        bar.setPrefHeight(GAME_BAR_HEIGHT);
        bar.getStyleClass().add("layout-bar");

        // stats
        VBox statBox = new VBox(10);
        statBox.getChildren().addAll(healthBar, attackBar);

        //arrows
        HBox arrowBoxB = new HBox();
        arrowBoxB.getStyleClass().add("layout-centered-spacing");
        styleButton(right);
        styleButton(up);
        styleButton(left);
        styleButton(down);
        right.setOnAction((event) -> {
            controller.moveRight();
            System.out.println("Right");
        });
        up.setOnAction((event) -> {
            controller.moveUp();
            System.out.println("Up");
        });
        left.setOnAction((event) -> {
            controller.moveLeft();
            System.out.println("Left");
        });
        down.setOnAction((event) -> {
            controller.moveDown();
            System.out.println("Down");
        });

        arrowBoxB.getChildren().addAll(left, down, right);

        // arrow descriptions
        Label topInstrucLabel = new Label("W to move up");
        Label bottomInstrucLabel = new Label("A to move left | S to move down | D to move right");
        topInstrucLabel.getStyleClass().add("label-description-tiny");
        bottomInstrucLabel.getStyleClass().add("label-description-tiny");
        VBox arrowBox = new VBox();
        arrowBox.getStyleClass().add("layout-centered-spacing");
        arrowBox.getChildren().addAll(topInstrucLabel, up, arrowBoxB, bottomInstrucLabel);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // score
        VBox scoreBox = new VBox();
        scoreBox.getStyleClass().add("layout-metric");
        Label scoreLabel = new Label("SCORE");
        scoreLabel.getStyleClass().add("label-metric-name");
        scoreNumber.getStyleClass().add("label-metric-value");
        scoreBox.getChildren().addAll(scoreLabel, scoreNumber);

        // level
        VBox levelBox = new VBox();
        levelBox.getStyleClass().add("layout-metric");
        Label levelLabel = new Label("LEVEL");
        levelLabel.getStyleClass().add("label-metric-name");
        levelNumber.getStyleClass().add("label-metric-value");
        levelBox.getChildren().addAll(levelLabel, levelNumber);

        bar.getChildren().addAll(statBox, arrowBox, spacer, scoreBox, levelBox);
        return bar;
    }

    private void styleButton(Button button) {
        button.setPrefSize(14, 14);
        button.getStyleClass().add("button-small");
    }

    public void setScore(int score) {
        scoreNumber.setText(Integer.toString(score));
    }

    private void loadImages() {
        String[] toLoad = {"misc/UNCHeart.png", "buff/UNCPencil_Buff.png", "misc/UNCHeartExtra.png"};
        for (String path : toLoad) {
            images.put(path, new Image(path));
        }
    }

    private ImageView getView(String path) {
        ImageView rtn = new ImageView(images.get(path));
        rtn.setFitWidth(TILE_SIZE / 2.0);
        rtn.setFitHeight(TILE_SIZE / 2.0);
        return rtn;
    }

    public synchronized void setHealth(int health) {
        healthBar.getChildren().clear();
        healthBar.setPrefHeight(TILE_SIZE / 2.0);
        healthBar.setMinHeight(TILE_SIZE / 2.0);
        healthBar.setMaxHeight(TILE_SIZE / 2.0);
        healthBar.setPrefWidth(TILE_SIZE * 5);

        int totalHearts = 10;
        if (health > 100) {
            for (int heart = health; heart > 100; heart -= 20) {
                healthBar.getChildren().add(getView("misc/UNCHeartExtra.png"));
                totalHearts--;
                if (totalHearts == 0) {
                    break;
                }
            }

            for (int i = 0; i < totalHearts; i++) {
                healthBar.getChildren().add(getView("misc/UNCHeart.png"));
            }
        } else {
            for (int i = 0; i < health / 10; i++) {
                healthBar.getChildren().add(getView("misc/UNCHeart.png"));
            }
        }
    }

    public void setAttack(int attack) {
        attackBar.getChildren().clear();
        for (int i = 0; i < attack / 5; i++) {
            attackBar.getChildren().add(getView("buff/UNCPencil_Buff.png"));
            if (i == 9) {
                break;
            }
        }
    }

    public void setLevel(int level) {
        levelNumber.setText(Integer.toString(level));
    }

    public void updateStats(GameStats gameStats) {
        setHealth(gameStats.getHealth());
        setAttack(gameStats.getAttack());
        setScore(gameStats.getScore());
        setLevel(gameStats.getLevel());
    }
}

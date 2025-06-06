package edu.unc.comp301.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import java.util.*;

public class GameBar implements FXComponent{
    private final Label scoreNumber = new Label("0");
    private final Label levelNumber = new Label("1");
    private final HBox healthBar = new HBox();
    private final Map<String, Image> images = new HashMap<>();
    private final int tileSize = 32;

    @Override
    public Parent render() {
        loadImages();
        HBox bar = new HBox();
        bar.setPrefWidth(640);
        bar.setPrefHeight(64);

        // health
        setHealth(32);

        // spacer
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // score
        VBox scoreBox = new VBox();
        Label scoreLabel = new Label("SCORE");
        scoreBox.getChildren().addAll(scoreLabel, scoreNumber);

        // level
        VBox levelBox = new VBox();
        Label levelLabel = new Label("LEVEL");
        levelBox.getChildren().addAll(levelLabel, levelNumber);

        bar.getChildren().addAll(healthBar, spacer, scoreBox, levelBox);
        return bar;
    }

    public void setScore(int score) {
        scoreNumber.setText(Integer.toString(score));
    }

    private void loadImages() {
        String[] toLoad = {"UNCHeart.png"};
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

    public void setHealth(int health) {
        healthBar.getChildren().clear();
        for (int i = 0; i < health / 10; i++) {
            healthBar.getChildren().add(getView("UNCHeart.png"));
        }
//        System.out.println("loaded health");
    }

    public void setLevel(int level) {
        levelNumber.setText(Integer.toString(level));
    }

    public void updateStats(int health, int score, int level) {
        setHealth(health);
        setScore(score);
        setLevel(level);
    }
}

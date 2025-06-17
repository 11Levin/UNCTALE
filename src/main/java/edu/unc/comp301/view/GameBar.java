package edu.unc.comp301.view;

import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import java.util.*;

public class GameBar implements FXComponent {
    private final Label scoreNumber = new Label("0");
    private final Label levelNumber = new Label("1");
    private final HBox healthBar = new HBox();
    private final HBox attackBar = new HBox();
    private final Map<String, Image> images = new HashMap<>();
    private final int tileSize = 32;

    @Override
    public Parent render() {
        loadImages();
        HBox bar = new HBox();
        bar.getStylesheets().add("main.css");
        bar.getStyleClass().add("bar");
        bar.setPrefWidth(640);
        bar.setPrefHeight(64);

        // stats
        setHealth(100);
        setAttack(20);
        VBox statBox = new VBox();
        statBox.getStylesheets().add("main.css");
        statBox.getChildren().addAll(healthBar,attackBar);


        // spacer
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // score
        VBox scoreBox = new VBox();
        scoreBox.getStyleClass().add("score");
        Label scoreLabel = new Label("SCORE");
        scoreLabel.getStyleClass().add("score-label");
        scoreBox.getChildren().addAll(scoreLabel, scoreNumber);

        // level
        VBox levelBox = new VBox();
        levelBox.getStyleClass().add("level");
        Label levelLabel = new Label("LEVEL");
        levelLabel.getStyleClass().add("level-label");
        levelBox.getChildren().addAll(levelLabel, levelNumber);

        bar.getChildren().addAll(statBox,spacer,scoreBox,levelBox);
        return bar;
    }

    public void setScore(int score) {
        scoreNumber.setText(Integer.toString(score));
    }

    private void loadImages() {
        String[] toLoad = {"misc/UNCHeart.png","buff/UNCPencil_Buff.png","misc/UNCHeartExtra.png"};
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

    public synchronized void setHealth(int health) {
        healthBar.getChildren().clear();

        int totalHearts = 10;
        if(health>100){
            for (int heart = health; heart > 100; heart -= 20) {
                healthBar.getChildren().add(getView("misc/UNCHeartExtra.png"));
                totalHearts--;
                if(totalHearts == 0){
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
//        System.out.println("loaded health");
    }

    public void setAttack(int attack) {
        attackBar.getChildren().clear();
        for (int i = 0; i < attack / 5; i++) {
            attackBar.getChildren().add(getView("buff/UNCPencil_Buff.png"));
            if (i == 10){
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

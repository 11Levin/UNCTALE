package edu.unc.comp301.view;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.piece.GameStats;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.*;

public class GameBar extends GameElement implements FXComponent{
    private final Label scoreNumber = new Label("0");
    private final Label levelNumber = new Label("1");
    private final HBox healthBar = new HBox();
    private final HBox attackBar = new HBox();
    private final Map<String, Image> images = new HashMap<>();
    private final Controller controller;
    private final Button right = new Button("D");
    private final Button up = new Button("W");
    private final Button left = new Button("A");
    private final Button down = new Button("S");

    public GameBar(Controller controller){
        this.controller = controller;
    }

    @Override
    public Parent render() {
        loadImages();
        HBox bar = new HBox();
        bar.setPrefWidth((2 * getHorizontalRadius() + 1) * TILE_SIZE);
        bar.setPrefHeight(GAME_BAR_HEIGHT);

        // stats
        VBox statBox = new VBox();
        statBox.getChildren().addAll(healthBar,attackBar);
        statBox.setSpacing(10);

        //arrows
        HBox arrowBoxT = new HBox();
        HBox arrowBoxB = new HBox();
        arrowBoxT.setAlignment(Pos.CENTER);
        arrowBoxB.setAlignment(Pos.CENTER);
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
        arrowBoxT.getChildren().addAll(up);
        arrowBoxB.getChildren().addAll(left,down,right);
        Label topInstrucLabel = new Label("Use W to move up");
        Label bottomInstruc = new Label("Use A to move left | S to move down | D to move right");
        topInstrucLabel.getStyleClass().add("instruction");
        bottomInstruc.getStyleClass().add("instruction");
        VBox arrowBox = new VBox();
        HBox topInstruc = new HBox();
        topInstruc.getStyleClass().add("instruction-box");
        topInstruc.getChildren().add(topInstrucLabel);
        arrowBox.getChildren().addAll(topInstruc,arrowBoxT,arrowBoxB,bottomInstruc);

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


        bar.getChildren().addAll(statBox,arrowBox,scoreBox,spacer,levelBox);
        return bar;
    }

    private void styleButton(Button button) {
        button.setMinWidth(30);
        button.setMaxWidth(30);
        button.setMinHeight(40);
        button.setMaxHeight(40);
        button.getStyleClass().add("arrow-Button");
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
            if (i == 9){
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

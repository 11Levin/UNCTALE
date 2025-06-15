package edu.unc.comp301.controller;

import edu.unc.comp301.model.Model;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.*;

public class Controller {
    private final Model model;
    private final Set<KeyCode> pressed = new HashSet<>();
    private int tick;

    public Controller(Model model) {
        this.model = model;
    }

    public void moveUp() {
        model.moveUp();
    }

    public void moveDown() {
        model.moveDown();
    }

    public void moveLeft() {
        model.moveLeft();
    }

    public void moveRight() {
        model.moveRight();
    }

    public void startGame() {
        model.startGame();
    }

    public void init(Scene scene) {
        // set keybinds
        scene.setOnKeyPressed(keyEvent -> {
            pressed.add(keyEvent.getCode());
        });

        Thread gameTick = new Thread(() -> {
            while (true) {
                if (model.getStatus() == Model.STATUS.GAME_CONTINUE) {
                    // check for player input
                    if (pressed.contains(KeyCode.UP) || pressed.contains(KeyCode.W)) {
                        moveUp();
                        pressed.remove(KeyCode.UP);
                        pressed.remove(KeyCode.W);
                    } else if (pressed.contains(KeyCode.LEFT) || pressed.contains(KeyCode.A)) {
                        moveLeft();
                        pressed.remove(KeyCode.LEFT);
                        pressed.remove(KeyCode.A);
                    } else if (pressed.contains(KeyCode.DOWN) || pressed.contains(KeyCode.S)) {
                        moveDown();
                        pressed.remove(KeyCode.DOWN);
                        pressed.remove(KeyCode.S);
                    } else if (pressed.contains(KeyCode.RIGHT) || pressed.contains(KeyCode.D)) {
                        moveRight();
                        pressed.remove(KeyCode.RIGHT);
                        pressed.remove(KeyCode.D);
                    }

                    // move enemies
                    if (tick++ % 20 == 0) {
                        //System.out.println("Enemies moved!");
                        model.spawnBullet();
                    }

                    // update views
                    Platform.runLater(model::updateViews);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        gameTick.start();
    }
}

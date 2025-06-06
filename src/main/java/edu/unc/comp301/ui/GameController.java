package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.CollisionEvent;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.security.Key;
import java.util.*;

public class GameController {
    private final BoardImpl board;
    private final View view;
    private Set<KeyCode> pressed = new HashSet<>();

    public GameController(BoardImpl board, View view) {
        this.board = board;
        this.view = view;
    }

    public void init(Scene scene) {
        // set keybinds
        scene.setOnKeyPressed(keyEvent -> {
            pressed.add(keyEvent.getCode());
//            System.out.println("Pressed " + keyEvent.getCode());
        });

        scene.setOnKeyReleased(keyEvent -> {
            pressed.remove(keyEvent.getCode());
        });

        Thread gameTick = new Thread(() -> {
            while (true) {
                // check movement
//                System.out.println(pressed);
                List<Posn> toUpdate = null;
                CollisionEvent res = null;
                KeyCode[] directions = {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
                for (KeyCode direction : directions) {
                    if (pressed.contains(direction)) {
                        res = board.move(direction);
                        toUpdate = res.getToUpdate();
                        break;
                    }
                }
                if (res != null && res.getResults().equals(CollisionEvent.Result.NEXT_LEVEL)) {
                    scene.setRoot(view.render());
                }
                if (toUpdate != null) {
                    final List<Posn> temp = toUpdate;
                    Platform.runLater(() -> {
                        view.update(temp);
                    });
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gameTick.start();
    }

}

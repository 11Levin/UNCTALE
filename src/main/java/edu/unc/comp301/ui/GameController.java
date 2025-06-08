package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.APiece;
import edu.unc.comp301.pieces.Character;
import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.EnemyImpl;
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
//            pressed.remove(keyEvent.getCode());
        });

        Thread gameTick = new Thread(() -> {
            while (true) {
                // check movement
                CollisionEvent res = null;
                KeyCode[] directions = {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT};
                for (KeyCode direction : directions) {
                    if (pressed.contains(direction)) {
                        pressed.remove(direction);
                        res = board.move(direction);
                        break;
                    }
                }

                if (res != null) { // redraw screen
                    // next level, redraw completely
                    if (res.getResults().equals(CollisionEvent.Result.NEXT_LEVEL)) {
                        scene.setRoot(view.render());
                    }
                    final CollisionEvent temp = res;
                    Platform.runLater(() -> {
                        view.update(temp);
                    });
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gameTick.start();


        Thread enemyLoad = new Thread(() -> {
            while (true) {
                boolean el = board.getEnemyLoad();
                if(el){
                    ArrayList<Character> enemyList = board.getEnemyList();
                    System.out.println(enemyList);
                    if (enemyList != null){
                        System.out.println("WORK?");
                        for (Character enemy: enemyList){
                            newEnemy(enemy);
                        }
                    }
                    board.setEnemyLoad(false);
                    System.out.println(el);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        enemyLoad.start();




    }

    public void newEnemy(Character enemy){
        Thread enemyMove = new Thread(() -> {
            while (enemy.getHealth() > 0) {

                CollisionEvent res = null;
                int moveNum = (int)(Math.random() * 2);
                if (moveNum == 0){
                    res = enemy.moveUp(board);
                } else {
                    res = enemy.moveDown(board);
                }

                System.out.println(res);
                System.out.println(enemy.getPosn());

                if (res != null) { // redraw screen
                    final CollisionEvent temp = res;
                    Platform.runLater(() -> {
                        view.update(temp);
                    });
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            board.getEnemyList().remove(enemy);
        });
        enemyMove.start();
    }

}

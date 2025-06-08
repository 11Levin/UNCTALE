package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;
import edu.unc.comp301.pieces.CollisionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.util.List;

public class View implements FXComponent{
    private final BoardImpl board;
    private Scene scene;
    private final GameView gameView;

    /**
     * Only call after board has been initialized
     * @param board
     */
    public View(BoardImpl board) {
        this.board = board;
        gameView = new GameView(board);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void update(CollisionEvent toUpdate) {
        gameView.update(toUpdate);
    }

    public void update(CollisionEvent toUpdate, Posn posn) {
        gameView.update(toUpdate, posn);
    }

    @Override
    public Parent render() {
        return gameView.render();
    }

}

package edu.unc.comp301.view;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.pieces.CollisionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class View implements FXComponent{
    private final BoardImpl board;
    private Scene scene;
    private final GameView gameView;
    private CollisionEvent.Result gameState = CollisionEvent.Result.GAME_START;
    private EndView endView;

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


    @Override
    public Parent render() {
        switch (gameState) {
            case CollisionEvent.Result.GAME_START:
                return (new StartView(this)).render();
            case CollisionEvent.Result.CONTINUE:
                return gameView.render();
            case CollisionEvent.Result.GAME_OVER:
                return endView.render();
            default:
                throw new IllegalArgumentException("gameState should be GAME_START, CONTINUE, GAME_OVER");
        }
    }

    public void startGame() {
        gameState = CollisionEvent.Result.CONTINUE;
        GameController gameController = new GameController(board, this);
        gameController.init(scene);
        scene.setRoot(gameView.render());
    }

    public void endGame() {
        gameState = CollisionEvent.Result.GAME_OVER;
        endView = new EndView(board.getPoints(), board.getPoints(), 0);
        scene.setRoot(endView.render());
    }
}

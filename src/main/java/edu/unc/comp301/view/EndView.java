package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndView extends View {
    private GameStats gameStats;

    public EndView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameStats = new GameStats(0, 0, 0, 0, 0);
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        Label scoreName = new Label("Score: ");
        Label scoreNumber = new Label(Integer.toString(gameStats.getScore()));
        Label highName = new Label("High: ");
        Label highNumber = new Label(Integer.toString(gameStats.getHighScore()));
        Button playAgain = new Button("Play again");
        playAgain.setOnAction((event) -> {
            controller.startGame();
        });
        vBox.getChildren().addAll(scoreName, scoreNumber, highName, highNumber, playAgain);
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_END) return;
        this.gameStats = gameStats;
        stage.getScene().setRoot(render());
    }
}

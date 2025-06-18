package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndView extends View {
    private GameStats gameStats;
    private int difficulty = 0;

    public EndView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameStats = new GameStats(0, 0, 0, 0, 0);
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        vBox.getStylesheets().add("main.css");
        vBox.getStyleClass().add("layout");
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setSpacing(20);

        HBox scoreBox = new HBox();
        scoreBox.getStyleClass().add("score-box");
        Label scoreName = new Label("Score: ");
        Label scoreNumber = new Label(Integer.toString(gameStats.getScore()));
        scoreName.getStyleClass().add("score-value");
        scoreBox.getChildren().addAll(scoreName,scoreNumber);

        HBox highBox = new HBox();
        highBox.getStyleClass().add("high-box");
        Label highName = new Label("High: ");
        Label highNumber = new Label(Integer.toString(gameStats.getHighScore()));
        highName.getStyleClass().add("high-value");
        highBox.getChildren().addAll(highName,highNumber);


        Button playAgain = new Button("Play again");
        playAgain.getStyleClass().add("again-button");
        playAgain.setOnAction((event) -> {
            controller.startGame(difficulty);
        });
        vBox.getChildren().addAll(scoreBox, highBox, playAgain);
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_END) return;
        this.gameStats = gameStats;
        stage.getScene().setRoot(render());
        this.difficulty = model.getDifficulty();
    }
}

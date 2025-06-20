package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;

public class EndView extends View {
    private GameStats gameStats;
    private int difficulty = 0;

    public EndView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
        gameStats = new GameStats(0, 0, 0, 0, 0, 0, new HashSet<>());
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        vBox.getStylesheets().add("main.css");
        vBox.getStyleClass().add("layout");
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setSpacing(40);

        HBox scoreBox = new HBox();
        scoreBox.getStyleClass().add("score-box");
        Label scoreName = new Label("Score: ");
        Label scoreNumber = new Label(Integer.toString(gameStats.getScore()));
        scoreNumber.getStyleClass().add("score-value");
        scoreName.getStyleClass().add("score-value");
        scoreBox.getChildren().addAll(scoreName, scoreNumber);

        HBox highBox = new HBox();
        highBox.getStyleClass().add("high-box");
        Label highName = new Label("Highscore: ");
        Label highNumber = new Label(Integer.toString(gameStats.getHighScore()));
        highNumber.getStyleClass().add("high-value");
        highName.getStyleClass().add("high-value");
        highBox.getChildren().addAll(highName, highNumber);

        HBox lastBox = new HBox();
        lastBox.getStyleClass().add("last-box");
        Label lastName = new Label("Last score: ");
        Label lastNumber = new Label(Integer.toString(gameStats.getLastScore()));
        lastNumber.getStyleClass().add("last-value");
        lastName.getStyleClass().add("last-value");
        lastBox.getChildren().addAll(lastName, lastNumber);


        Button playAgain = new Button("Play again");
        playAgain.getStyleClass().add("again-button");
        playAgain.setOnAction((event) -> {
            controller.startGame(difficulty);
        });

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("exit-button");


        exitButton.setOnAction((event) -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(scoreBox, highBox, lastBox, playAgain, exitButton);
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

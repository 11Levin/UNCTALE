package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartView extends View {
    private int difficulty = 0;
    private static final String[] difficultyLevels = {"Easy", "Medium", "Hard"};

    public StartView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
    }

    @Override
    public Parent render() {
        // container for everything
        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStylesheets().add("main.css");

        // game name
        Label titleLabel = new Label("UNC Tale");
        VBox titleBox = new VBox();
        titleBox.getChildren().add(titleLabel);
        titleBox.getStyleClass().add("layout");
        titleBox.getStyleClass().add("layout-title");
        titleLabel.getStyleClass().add("label-title");

        // credits
        VBox subtitleBox = new VBox();
        Label subtitle = new Label("Made by Vincent Wang and Levin Wu");
        subtitleBox.getChildren().add(subtitle);
        subtitleBox.getStyleClass().add("layout");
        subtitleBox.getStyleClass().add("layout-subtitle");
        subtitle.getStyleClass().add("label-subtitle");

        // buttons
        Button startButton = new Button("Start");
        startButton.setOnAction((event) -> {
            controller.startGame(difficulty);
        });
        startButton.getStyleClass().add("button");

        Button instructionsButton = new Button("Instructions");
        instructionsButton.setOnAction((event) -> {
            System.out.println("Instructions pressed");
            model.showInstructions();
        });
        instructionsButton.getStyleClass().add("button");

        Button difficultyButton = new Button("Difficulty: " + difficultyLevels[difficulty]);
        difficultyButton.setOnAction((event) -> {
            difficulty = (difficulty + 1) % difficultyLevels.length;
            difficultyButton.setText("Difficulty: " + difficultyLevels[difficulty]);
        });
        difficultyButton.getStyleClass().add("button");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction((event) -> {
            Platform.exit();
        });
        exitButton.getStyleClass().add("button-important");

        vBox.getChildren().addAll(titleBox, subtitleBox, startButton,difficultyButton,instructionsButton, exitButton);

        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_START) return;
        stage.getScene().setRoot(render());
    }
}

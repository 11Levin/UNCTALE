package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0, 0, 40, 0));
        vBox.setAlignment(Pos.CENTER);
        vBox.getStylesheets().add("main.css");
        vBox.getStyleClass().add("layout");


        Label titleLabel = new Label("UNC Tale");
        VBox titleBox = new VBox();

        VBox subtitleBox = new VBox();
        Label subtitle = new Label("Made by Vincent and Levin Wu");
        subtitle.getStyleClass().add("subtitle");
        subtitleBox.getChildren().add(subtitle);

        HBox scoresBox = new HBox(20);

        HBox scoreBox = new HBox();
        scoreBox.getStyleClass().add("score-box_start");
        Label scoreName = new Label("Score: ");
        Label scoreNumber = new Label("0");
        scoreName.getStyleClass().add("score-value_start");
        scoreBox.getChildren().addAll(scoreName,scoreNumber);

        HBox highBox = new HBox();
        highBox.getStyleClass().add("high-box_start");
        Label highName = new Label("Highscore: ");
        Label highNumber = new Label("0");
        highName.getStyleClass().add("high-value_start");
        highBox.getChildren().addAll(highName,highNumber);

        HBox lastBox = new HBox();
        lastBox.getStyleClass().add("last-box_start");
        Label lastName = new Label("Last score: ");
        Label lastNumber = new Label("0");
        lastName.getStyleClass().add("last-value_start");
        lastBox.getChildren().addAll(lastName,lastNumber);

        scoresBox.getChildren().addAll(scoreBox,highBox,lastBox);
        scoresBox.setAlignment(Pos.CENTER);

        titleLabel.getStyleClass().add("title");
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().add(titleLabel);

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("start-button");


        startButton.setOnAction((event) -> {
            controller.startGame(difficulty);
        });

        Button instructionsButton = new Button("Instructions");
        instructionsButton.getStyleClass().add("instruction-button");
        instructionsButton.setOnAction((event) -> {
            System.out.println("Instructions pressed");
            model.showInstructions();
        });

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("exit-button");


        exitButton.setOnAction((event) -> {
            Platform.exit();
        });

        Button difficultyButton = new Button("Difficulty: " + difficultyLevels[difficulty]);
        difficultyButton.getStyleClass().add("start-button");
        difficultyButton.setOnAction((event) -> {
            difficulty = (difficulty + 1) % difficultyLevels.length;
            difficultyButton.setText("Difficulty: " + difficultyLevels[difficulty]);
        });

        vBox.getChildren().addAll(titleBox, subtitleBox,scoresBox, startButton,difficultyButton,instructionsButton, exitButton);

        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_START) return;
        stage.getScene().setRoot(render());
    }
}
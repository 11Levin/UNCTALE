package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartView extends View {
    public StartView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
    }

    @Override
    public Parent render() {
        Label titleLabel = new Label("UNC Tale");
        Button startButton = new Button("Start");
        titleLabel.getStyleClass().add("title-label");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(titleLabel, startButton);
        startButton.setOnAction((event) -> {
            controller.startGame();
        });
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_START) return;
        stage.getScene().setRoot(render());
    }
}

package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructionsView extends View{
    public InstructionsView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        Button homeButton = new Button("Home");
        Label instructionsLabel = new Label("Instructions");
        Label instructionsText = new Label("UNC has been taken over by evil Ramses! Use WASD or the arrow keys to navigate your player to avoid enemies, collect treasure, buff your player, and defeat Ramses!");
        instructionsText.setWrapText(true);
        vBox.getChildren().addAll(homeButton, instructionsLabel, instructionsText);

        homeButton.setOnAction((event) -> {
            System.out.println("Home button pressed");
            model.showHomeScreen();
        });
        return vBox;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_INSTRUCTIONS) return;
        stage.getScene().setRoot(render());
    }
}

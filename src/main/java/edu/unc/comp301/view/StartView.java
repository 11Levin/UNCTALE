package edu.unc.comp301.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartView implements FXComponent{
    View view;

    public StartView(View view) {
        this.view = view;
    }

    @Override
    public Parent render() {
        Label titleLabel = new Label("UNC Tale");
        titleLabel.getStyleClass().add("text");
        Button startButton = new Button("Start");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(titleLabel, startButton);
        startButton.setOnAction((event) -> {
            view.startGame();
        });
        return vBox;
    }
}

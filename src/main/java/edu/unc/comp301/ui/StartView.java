package edu.unc.comp301.ui;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartView implements FXComponent{
    View view;

    public StartView(View view) {
        this.view = view;
    }

    @Override
    public Parent render() {
        Label titleLabel = new Label("UNC Tale");
        titleLabel.getStyleClass().add("text");
        Font font = Font.loadFont(getClass().getResourceAsStream("fonts/joystix_monospace.otf"), 14);
        titleLabel.setFont(font);
        Button startButton = new Button("Start");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(titleLabel, startButton);
        startButton.setOnAction((event) -> {
            view.startGame();
        });
        return vBox;
    }
}

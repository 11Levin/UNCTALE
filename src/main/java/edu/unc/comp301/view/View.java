package edu.unc.comp301.view;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class View extends GameElement implements FXComponent, Observer {
    protected final Controller controller;
    protected final Model model;
    protected final Stage stage;

    public View(Controller controller, Model model, Stage stage) {
        this.controller = controller;
        this.model = model;
        this.stage = stage;

        stage.widthProperty().addListener((obs, oldValue, newValue) -> {
            if (model.getStatus() == Model.STATUS.GAME_CONTINUE) {
                int newHorizontalRadius = (newValue.intValue() / tileSize - 1) / 2;
                setHorizontalRadius(newHorizontalRadius);
                model.updateViews();
            }
        });
        stage.heightProperty().addListener((obs, newValue, oldValue) -> {
            if (model.getStatus() == Model.STATUS.GAME_CONTINUE) {
                int newVerticalRadius = (newValue.intValue() / tileSize - 1) / 2;
                setVerticalRadius(newVerticalRadius);
                System.out.println("new vr: " + newVerticalRadius);
                model.updateViews();
            }
        });
    }

    @Override
    public Parent render() {
        StackPane layout = new StackPane();
        layout.getStylesheets().add("main.css");
        layout.getStyleClass().add("layout");
        Label label = new Label("Hello World");
        layout.getChildren().add(label);
        return layout;
    }

    @Override
    public void update(GameStats gameStats) {
        stage.getScene().setRoot(render());
    }
}

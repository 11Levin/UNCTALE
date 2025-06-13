package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.Observer;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class View implements FXComponent, Observer {
    protected final Controller controller;
    protected final Model model;
    protected final Stage stage;

    public View(Controller controller, Model model, Stage stage) {
        this.controller = controller;
        this.model = model;
        this.stage = stage;
    }

    @Override
    public Parent render() {
        StackPane layout = new StackPane();
        Label label = new Label("Hello World");
        layout.getChildren().add(label);
        return layout;
    }

    @Override
    public void update(GameStats gameStats) {
        stage.getScene().setRoot(render());
    }
}

package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.board.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    private final int[] dims = {9, 9};
    private final int tileSize = 64;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // model
        Board board = new Board();
        Model model = new Model(board);

        // controller
        Controller controller = new Controller(model);

        // view
        StartView startView = new StartView(controller, model, primaryStage);
        GameView gameView = new GameView(controller, model, primaryStage);
        EndView endView = new EndView(controller, model, primaryStage);
        model.addView(startView);
        model.addView(gameView);
        model.addView(endView);

        // setup stage
        Scene scene = new Scene(startView.render(), tileSize * dims[0], tileSize * (dims[1] + 3));
        controller.init(scene);
        scene.getStylesheets().add("fontstyle.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNCTale");
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}

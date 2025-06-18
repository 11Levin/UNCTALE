package edu.unc.comp301.view;

import edu.unc.comp301.GameElement;
import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.board.Board;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppLauncher extends Application {

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
        InstructionsView instructionsView = new InstructionsView(controller, model, primaryStage);
        model.addView(startView);
        model.addView(gameView);
        model.addView(endView);
        model.addView(instructionsView);

        Rectangle2D rect = Screen.getPrimary().getBounds();
        double screenWidth = rect.getWidth();
        double screenHeight = rect.getHeight() - 80;
        Scene scene = new Scene(startView.render(), screenWidth, screenHeight);
        GameElement.setVerticalRadius(((int)(screenHeight / GameElement.tileSize) - 1) / 2);
        GameElement.setHorizontalRadius(((int)(screenWidth / GameElement.tileSize) - 1) / 2);

        controller.init(scene);
        scene.getStylesheets().add("fontstyle.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNCTale");
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}

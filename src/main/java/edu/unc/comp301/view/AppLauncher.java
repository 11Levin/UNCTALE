package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.board.Board;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static edu.unc.comp301.GameElement.*;

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

        // set stage size
        Rectangle2D rect = Screen.getPrimary().getBounds();
        int verticalRadius = (((int)rect.getHeight() - GAME_BAR_HEIGHT - 3 * STAGE_PADDING) / TILE_SIZE - 1) / 2;
        int horizontalRadius = (((int)rect.getWidth() - STAGE_PADDING) / TILE_SIZE - 1) / 2;
        horizontalRadius = Math.min(horizontalRadius, verticalRadius + 2);
        View.setVerticalRadius(verticalRadius);
        View.setHorizontalRadius(horizontalRadius);

        Scene scene = new Scene(startView.render(), (2 * horizontalRadius + 1) * TILE_SIZE, (2 * verticalRadius + 1) * TILE_SIZE + GAME_BAR_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        controller.init(scene);
//        scene.getStylesheets().add("fontstyle.css");
        primaryStage.setTitle("UNCTale");
        primaryStage.show();
    }
}

package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.exceptions.LevelNotLoadedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class AppLauncher extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
      BoardImpl board = new BoardImpl(1, 1);
      try {
          board.init(1);
      } catch (FileNotFoundException e) {
          throw new LevelNotLoadedException("level couldn't be loaded");
      }
      View view = new View(board);
      Scene scene = new Scene(view.render(), 640, 704);
      view.setScene(scene);
      GameController gameController = new GameController(board, view);
      gameController.init(scene);
      primaryStage.setScene(scene);
      primaryStage.setTitle("UNCTale");
      primaryStage.setResizable(false);
      primaryStage.show();
  }
}

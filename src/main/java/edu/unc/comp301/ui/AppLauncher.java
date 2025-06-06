package edu.unc.comp301.ui;

import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.exceptions.LevelNotLoadedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class AppLauncher extends Application {
    private final int[] dims = {9, 9};
    private final int tileSize = 64;
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
      Scene scene = new Scene(view.render(), tileSize * dims[0], tileSize * (dims[1] + 1));
      view.setScene(scene);

      GameController gameController = new GameController(board, view);
      gameController.init(scene);

      primaryStage.setScene(scene);
      primaryStage.setTitle("UNCTale");
      primaryStage.setResizable(true);
      primaryStage.show();
  }
}

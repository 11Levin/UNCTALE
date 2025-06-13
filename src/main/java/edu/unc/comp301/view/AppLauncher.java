package edu.unc.comp301.view;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.exception.LevelNotLoadedException;
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
      Board board = new Board(1, 1);
      board.init(1);
      View view = new View(board);
      Scene scene = new Scene(view.render(), tileSize * dims[0], tileSize * (dims[1] + 1));
      view.setScene(scene);
      scene.getStylesheets().add("main.css");
      primaryStage.setScene(scene);
      primaryStage.setTitle("UNCTale");
      primaryStage.setResizable(true);
      primaryStage.show();
  }
}

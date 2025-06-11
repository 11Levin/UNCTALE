package edu.unc.comp301.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndView implements FXComponent{
    private int score, high, time;


    /**
     * Displays the game end screen
     * @param score the player's current score
     * @param high the high score
     * @param time the time taken to beat the game
     */
    public EndView(int score, int high, int time) {
        this.score = score;
        this.high = high;
        this.time = time;
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox();
        Label scoreName = new Label("Score: ");
        Label scoreNumber = new Label(Integer.toString(score));
        Label highName = new Label("High: ");
        Label highNumber = new Label(Integer.toString(high));
        Label timeName = new Label("Time: ");
        Label timeLabel = new Label(Integer.toString(time));
        Button playAgain = new Button("Play again");
        vBox.getChildren().addAll(scoreName, scoreNumber, highName, highNumber, timeName, timeLabel, playAgain);
        return vBox;
    }
}

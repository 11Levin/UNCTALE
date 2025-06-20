package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.piece.GameStats;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructionsView extends View{
    public InstructionsView(Controller controller, Model model, Stage stage) {
        super(controller, model, stage);
    }

    @Override
    public Parent render() {
        VBox vBox = new VBox(10);
        vBox.getStyleClass().add("instruc-home");
        Button homeButton = new Button("Home");
        homeButton.getStyleClass().add("home-Button");
        Label instructionsLabel = new Label("Instructions");
        Label instructionsText = new Label("UNC has been taken over by evil Ramses! Use WASD or the arrow keys to navigate your player to avoid the bullets, collect treasure, buff your player, and defeat Ramses! To attack enemies just run into them but be careful of the magic Ramses propoganda bullets that enemies spawn. Complete the 5 levels and beat Ramses to free the school.");
        instructionsText.setWrapText(true);
        VBox itemBox = new VBox(5);
        itemBox.getChildren().add(createItemRow("movable/UNCPlayer.png","This is you the player, what a handsome fellow."));
        itemBox.getChildren().add(createItemRow("movable/UNCStudent_Enemy.png","A brainwashed student, the weakest enemy and drops an apple upon defeat."));
        itemBox.getChildren().add(createItemRow("movable/UNCProfessor_Enemy.png","A brainwashed professor, their high IQ makes them stronger and drops a pencil upon defeat"));
        itemBox.getChildren().add(createItemRow("movable/UNCRamses_Enemy.png","The evil mastermind, doesn't move but shoots magic bullets from all sides."));
        itemBox.getChildren().add(createItemRow("movable/UNCBullet_Enemy.png","Evil Ramses Propoganda Bullet. Damages the player."));
        itemBox.getChildren().add(createItemRow("immovable/UNCLogo_Exit.png","An Exit! Move on to the next level"));
        itemBox.getChildren().add(createItemRow("immovable/UNCMoney_Treasure.png","Who doesn't love free money? Increase player score."));
        itemBox.getChildren().add(createItemRow("buff/UNCApple_Buff.png","An apple is great for your health. Increase player health."));
        itemBox.getChildren().add(createItemRow("buff/UNCPencil_Buff.png","What a sharp pencil. Increase player attack."));
        itemBox.getChildren().add(createItemRow("buff/UNCHomework_Debuff.png","Not more homework... Decrease player health and attack."));
        itemBox.getChildren().add(createItemRow("immovable/UNCRam_Obstacle.png","Just a ram. Don't try to move through it."));
        itemBox.getChildren().add(createItemRow("immovable/UNCPortal.png","Might take you to Davis Libary."));
        vBox.getChildren().addAll(instructionsLabel, instructionsText,itemBox,homeButton);

        homeButton.setOnAction((event) -> {
            System.out.println("Home button pressed");
            model.showHomeScreen();
        });
        return vBox;
    }

    private HBox createItemRow(String imagePath, String description) {
        ImageView image = new ImageView(new Image(imagePath));
        image.setFitWidth(30);
        image.setFitHeight(30);
        Label desc = new Label(description);
        desc.getStyleClass().add("desc");
        HBox row = new HBox(10, image, desc);
        return row;
    }

    @Override
    public void update(GameStats gameStats) {
        if (model.getStatus() != Model.STATUS.GAME_INSTRUCTIONS) return;
        stage.getScene().setRoot(render());
    }
}

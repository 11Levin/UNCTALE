package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Player;

public class PlayerImpl extends Character implements Player {
  public PlayerImpl(String name, String resourcePath, int health) {
    super(name, resourcePath, health);
  }

  @Override
  public void movePlayer(int drow, int dcol) {
    // to implement
  }

  /*
  ask to ensure that PLayer cannot move off the bounds of the board
   */
  public void movePlayer(Character.Direction direction) {
    switch (direction) {
      case UP:
        movePlayer(-1, 0);
      case DOWN:
        movePlayer(1, 0);
      case LEFT:
        movePlayer(0, -1);
      case RIGHT:
        movePlayer(0, 1);
    }
  }

  @Override
  public void attack() {}
}

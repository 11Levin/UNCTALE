package edu.unc.comp301.model.piece.decorator;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.CollisionEvent;
import edu.unc.comp301.model.piece.Piece;
import edu.unc.comp301.model.piece.movable.MovablePiece;
import edu.unc.comp301.model.piece.movable.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

/** Decorates the player with a buff */
public class PlayerDecorator implements PlayerInterface {
  protected final PlayerInterface playerInterface;

  public PlayerDecorator(PlayerInterface playerInterface) {
    this.playerInterface = playerInterface;
  }

  /**
   * Accesses the active buffs applied to the base player
   *
   * @return a list of strings representing buffs, with mappings given by
   *     resources/misc/entity_data.txt
   */
  @Override
  public List<String> getBuffs() {
    return new ArrayList<>();
  }

  /**
   * Accesses the base player's health
   *
   * @return health
   */
  @Override
  public int getHealth() {
    return playerInterface.getHealth();
  }

  /**
   * Sets the base player's health
   *
   * @param health health to set
   */
  @Override
  public void setHealth(int health) {
    playerInterface.setHealth(health);
  }

  /**
   * Accesses the base player's attack
   *
   * @return attack
   */
  @Override
  public int getAttack() {
    return playerInterface.getAttack();
  }

  /**
   * Sets the base player's attack
   *
   * @param attack attack to set
   */
  @Override
  public void setAttack(int attack) {
    playerInterface.setAttack(attack);
  }

  /**
   * Returns the result of a collision of this player with other
   *
   * @param other
   * @return
   */
  @Override
  public CollisionEvent collide(Piece other) {
    return playerInterface.collide(other);
  }

  /**
   * Moves the player
   *
   * @param drow change in row
   * @param dcol change in col
   */
  @Override
  public void move(int drow, int dcol) {
    playerInterface.move(drow, dcol);
  }

  /**
   * Causes this to attack the movable other
   *
   * @param attack
   * @param other
   */
  @Override
  public void attack(int attack, MovablePiece other) {
    playerInterface.attack(attack, other);
  }

  /**
   * @return "player"
   */
  @Override
  public String getName() {
    return playerInterface.getName();
  }

  /**
   * Accesses the base player's position
   *
   * @return
   */
  @Override
  public Posn getPosn() {
    return playerInterface.getPosn();
  }

  /**
   * Sets the base player's position
   *
   * @param posn position to set
   */
  @Override
  public void setPosn(Posn posn) {
    playerInterface.setPosn(posn);
  }

  /**
   * @return "resources/misc/movable/UNCPlayer.png"
   */
  @Override
  public String getResourcePath() {
    return playerInterface.getResourcePath();
  }

  /**
   * @return Type.MOVABLE
   */
  @Override
  public Type getType() {
    return playerInterface.getType();
  }
}

package edu.unc.comp301.model.piece.buff;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.piece.APiece;

public abstract class Buff extends APiece {
  private final int attack;
  private final int health;

  /**
   * Creates a buff
   * @param name the buff's name
   * @param resourcePath (name).png or (name).jpg
   * @param posn the buff's position
   * @param attack change in attack if player were to pick up this buff
   * @param health change in health
   */
  public Buff(String name, String resourcePath, Posn posn, int attack, int health) {
    super(name, resourcePath, posn, Type.BUFF);
    this.attack = attack;
    this.health = health;
  }

  public int getAttack() {return attack;}

  public int getHealth() {return health;}
}

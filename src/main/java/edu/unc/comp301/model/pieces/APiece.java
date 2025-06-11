package edu.unc.comp301.model.pieces;

import edu.unc.comp301.model.board.Posn;

public abstract class APiece implements Piece {
  private final String name;
  private final String resourcePath;
  private Posn position;

  public APiece(String name, String resourcePath) {
    this(name, resourcePath, new Posn(0, 0));
  }

  public APiece(String name, String resourcePath, Posn posn) {
    this.name = name;
    this.resourcePath = resourcePath;
    this.position = posn;
  }

  public CollisionEvent.Result determineCollision(Piece other) {
    String e1 = this.getName();
    String e2 = other.getName();
    CollisionEvent.Result res = CollisionEvent.Result.CONTINUE;
    if (e1.equals("Player") && e2.equals("Exit")) {
      res = CollisionEvent.Result.NEXT_LEVEL;
    }
    if (e1.equals("Player") && e2.equals("Enemy")) {
      res = CollisionEvent.Result.ATTACK;
    }
    if (e1.equals("Player") && e2.equals("Item")) {
      res = CollisionEvent.Result.PICK_ITEM;
    }
    if (e1.equals("Player") && e2.equals("Obstacle")) {
      res = CollisionEvent.Result.BLOCK;
    }
    if (e1.equals("Enemy") && e2.equals("Player")) {
      res = CollisionEvent.Result.HIT;
    }
    if (e1.equals("Enemy") && e2.equals("Obstacle")) {
      res = CollisionEvent.Result.BLOCK;
    }
    return res;
  }

  @Override
  public String getResourcePath() {
    return resourcePath;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public Posn getPosn() {
    return position;
  }

  @Override
  public void setPosn(Posn posn) {
    this.position = posn;
  }

  @Override
  public String toString() {
    return getName().substring(0, 1);
  }
}

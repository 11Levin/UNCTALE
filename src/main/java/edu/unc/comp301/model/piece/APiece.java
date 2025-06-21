package edu.unc.comp301.model.piece;

import edu.unc.comp301.model.board.Posn;

public abstract class APiece implements Piece {
  private final String name;
  private final String resourcePath;
  private final Type type;
  private Posn position;

  public APiece(String name, String resourcePath, Posn posn, Type type) {
    this.name = name;
    this.resourcePath = resourcePath;
    this.position = posn;
    this.type = type;
  }

  public APiece(String name, String resourcePath, Type type) {
    this(name, resourcePath, null, type);
  }

  @Override
  public String getResourcePath() {
    return resourcePath;
  }

  @Override
  public String getName() {
    return name;
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
  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }
}

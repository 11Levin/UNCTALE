package edu.unc.comp301.model.board;

/** Class representing a board position, i.e. an ordered pair of integers */
public class Posn {
  private final int row;
  private final int col;

  public Posn(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object other) {
    Posn oth = (Posn) other;
    return (row == oth.getRow()) && (col == oth.getCol());
  }

  @Override
  public String toString() {
    return row + "," + col;
  }
}

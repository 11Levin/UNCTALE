package edu.unc.comp301.pieces;

import edu.unc.comp301.board.Board;
import edu.unc.comp301.board.BoardImpl;
import edu.unc.comp301.board.Posn;

public class Character extends APiece implements MovablePiece {
  int health;

  /**
   * resourcePath should be "resources/(name).png"
   * @param name
   * @param resourcePath
   * @param posn
   * @param health
   */
  public Character(String name, String resourcePath, Posn posn, int health) {
    super(name, resourcePath, posn);
    this.health = health;
  }

  /**
   * Simulates a collision as if this were at the same position as other
   * @param other the piece to collide
   * @return the result of the collision
   */
  @Override
  public CollisionEvent collide(Piece other) {
    if (other == null) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
    switch (other.getName()) {
      case "obstacle":
        return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
      case "treasure":
        return new CollisionEvent(10, 0, 0, CollisionEvent.Result.PICK_ITEM);
      case "enemy":
        return new CollisionEvent(0, 0, 0, CollisionEvent.Result.GAME_OVER);
      default:
        return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
    }
  }

  /**
   * moves the player, ensuring player remains in board bounds, and gives result of move as a collision
   * @param dr change in row
   * @param dc change in column
   * @param board board for player to move in
   * @return the result of the move
   */
  private CollisionEvent move(int dr, int dc, Board board) {
    // check board bounds, and if out of bounds, do nothing.
    if (dr < 0 && getPosn().getRow() == 0) return null;
    if (dr > 0 && getPosn().getRow() == board.getHeight() - 1) return null;
    if (dc < 0 && getPosn().getCol() == 0) return null;
    if (dc > 0 && getPosn().getCol() == board.getWidth() - 1) return null;

    int nr = getPosn().getRow() + dr;
    int nc = getPosn().getCol() + dc;
    Posn nPos = new Posn(nr, nc);
    CollisionEvent res = collide(board.getPiece(nPos));

    // update position if no obstacle
    if (res.getResults() != CollisionEvent.Result.BLOCK) {
      setPosn(nPos);
    }
    if (res.getResults() == CollisionEvent.Result.PICK_ITEM) {
      BoardImpl boardImpl = (BoardImpl) board;
      boardImpl.setPoints(boardImpl.getPoints() + res.getPoints());
    }
    return res;
  }

  public CollisionEvent moveUp(Board board) {
    return move(-1, 0, board);
  }

  public CollisionEvent moveDown(Board board) {
    return move(1, 0, board);
  }

  public CollisionEvent moveLeft(Board board) {
    return move(0, -1, board);
  }

  public CollisionEvent moveRight(Board board) {
    return move(0, 1, board);
  }

  public int getHealth() {
    return health;
  }

  /**
   * Sets the character's health to the largest of parameter health and 0.
   * @param health
   */
  public void setHealth(int health) {
    this.health = Math.max(health, 0);
  }
}
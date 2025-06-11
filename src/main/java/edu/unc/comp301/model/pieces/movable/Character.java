package edu.unc.comp301.model.pieces.movable;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.APiece;
import edu.unc.comp301.model.pieces.CollisionEvent;
import edu.unc.comp301.model.pieces.Piece;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    List<Posn> toUpdate = new ArrayList<>();
    toUpdate.add(getPosn());
    if (other != null) toUpdate.add(other.getPosn());
    if (other == null) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
    if (this.getName().equals("player")){
      switch (other.getName()) {
        case "obstacle":
          return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
        case "treasure":
          return new CollisionEvent(10, 0, 0, CollisionEvent.Result.PICK_ITEM);
        case "pencil":
          return new CollisionEvent(0, 5, 0, CollisionEvent.Result.PICK_ITEM);
        case "apple":
          return new CollisionEvent(0, 0, 5, CollisionEvent.Result.PICK_ITEM);
        case "homework":
          return new CollisionEvent(0, -5, -10, CollisionEvent.Result.PICK_ITEM);
        case "enemy":
          return new CollisionEvent(0, 0, 0, CollisionEvent.Result.ATTACK);
        case "exit":
          return new CollisionEvent(20, 0, 0, CollisionEvent.Result.NEXT_LEVEL);
        default:
          return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
    }
    }else {
        switch (other.getName()) {
          case "player":
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.HIT);
          case "obstacle":
            return new CollisionEvent(10, 0, 0, CollisionEvent.Result.BLOCK);
          case "treasure":
            return new CollisionEvent(10, 0, 0, CollisionEvent.Result.BLOCK);
          case "pencil":
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
          case "apple":
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
          case "homework":
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
          case "enemy":
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
          case "exit":
            return new CollisionEvent(20, 0, 0, CollisionEvent.Result.BLOCK);
          default:
            return new CollisionEvent(0, 0, 0, CollisionEvent.Result.CONTINUE);
      }

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
    List<Posn> toUpdate = new ArrayList<>();
    toUpdate.add(getPosn());
    toUpdate.add(new Posn(getPosn().getRow() + dr, getPosn().getCol() + dc));
    if (dr < 0 && getPosn().getRow() == 0) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
    if (dr > 0 && getPosn().getRow() == board.getHeight() - 1) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
    if (dc < 0 && getPosn().getCol() == 0) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);
    if (dc > 0 && getPosn().getCol() == board.getWidth() - 1) return new CollisionEvent(0, 0, 0, CollisionEvent.Result.BLOCK);

    int nr = getPosn().getRow() + dr;
    int nc = getPosn().getCol() + dc;
    Posn nPos = new Posn(nr, nc);
    CollisionEvent res = collide(board.getPiece(nPos));
    // player attacks enemy if enemy is in the way. Makes enemy lose 10 health
    if (res.getResults() == CollisionEvent.Result.HIT) {
      res = new CollisionEvent(0, 0,0,CollisionEvent.Result.BLOCK);
    }


    if (res.getResults() == CollisionEvent.Result.ATTACK) {
      BoardImpl boardImpl = (BoardImpl) board;
      Character enemy = upcastPieceToCharacter(board.getPiece(nPos));
      PlayerImpl player = upcastPieceToPlayer(board.getPiece(getPosn()));
      player.attack(boardImpl.getAttack(), enemy);
      System.out.println("Enemy Hurt! Health is at: " + enemy.getHealth());
      if (enemy.getHealth() == 0) { // enemy will drop buff when killed

        boardImpl.getBoard()[nr][nc] = boardImpl.nameToPiece("pencil", new Posn(nr, nc));
        //res = new CollisionEvent(0, CollisionEvent.Result.CONTINUE);
        //((BoardImpl) board).getGameView().update(res);
      }
      res = new CollisionEvent(0, 0,0,CollisionEvent.Result.BLOCK);
    }
    // update position if no obstacle or enemy is in the way

    if (res.getResults() == CollisionEvent.Result.PICK_ITEM) {
      BoardImpl boardImpl = (BoardImpl) board;
      PlayerImpl player = upcastPieceToPlayer(board.getPiece(getPosn()));
      System.out.println(board.getPiece(nPos).getName());
      if (board.getPiece(nPos).getName().equals("treasure")) {
        boardImpl.setPoints(boardImpl.getPoints() + res.getPoints());
      } else if (board.getPiece(nPos).getName().equals("apple")) {
        boardImpl.setHealth(boardImpl.getHealth() + 20); // increase health by 20
        System.out.println(boardImpl.getHealth());
      } else if (board.getPiece(nPos).getName().equals("pencil")) {
        boardImpl.setAttack(boardImpl.getAttack() + 5); // increase attack by 5
      } else if (board.getPiece(nPos).getName().equals("homework")) {
        boardImpl.setAttack(boardImpl.getAttack() - 5);
        boardImpl.setHealth(boardImpl.getHealth() - 10);
        System.out.println(boardImpl.getHealth());
      } // decrease stats by 5
    }



    if (res.getResults() == CollisionEvent.Result.NEXT_LEVEL) {
      BoardImpl newBoard = (BoardImpl) board;
      try {
        PlayerImpl player = upcastPieceToPlayer(board.getPiece(getPosn()));
        player.toggleNext_level();
        //System.out.println(player.getNext_Level());
        //System.out.println(newBoard.getIntialPosn(newBoard.getLevel()));
        newBoard.init(newBoard.getLevel(),player);
        player.setPosn(newBoard.getIntialPosn(newBoard.getLevel()+1));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    if (res.getResults() != CollisionEvent.Result.BLOCK && res.getResults() != CollisionEvent.Result.NEXT_LEVEL) {
      BoardImpl newBoard = (BoardImpl) board;
      setPosn(nPos);
      newBoard.getBoard()[nr][nc] = (APiece) this;
      newBoard.getBoard()[nr-dr][nc-dc] = null;
    }

    return res;
  }

  // upcasts pieces to characters to use character methods.
  public Character upcastPieceToCharacter(Piece character) {
    Character UPcharacter = (Character) character;
    return UPcharacter;
  }

  // upcasts pieces to player to use player methods and fields.
  public PlayerImpl upcastPieceToPlayer(Piece character) {
    PlayerImpl UPcharacter = (PlayerImpl) character;
    return UPcharacter;
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

  public void attack(int attack, Character Opponent) {
    Opponent.setHealth(Opponent.getHealth() - attack);
  }

}
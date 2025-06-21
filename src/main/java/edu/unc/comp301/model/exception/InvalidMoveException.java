package edu.unc.comp301.model.exception;

/** Exception for invalid movement calls on movables */
public class InvalidMoveException extends IllegalArgumentException {
  public InvalidMoveException(String message) {
    super(message);
  }
}

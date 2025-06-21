package edu.unc.comp301.model.exception;

/** Exception for an incorrectly designed level with two players */
public class PlayerAlreadyFoundException extends IllegalArgumentException {
  public PlayerAlreadyFoundException(String message) {
    super(message);
  }
}

package edu.unc.comp301.model.exception;

public class InvalidMoveException extends IllegalArgumentException {
    public InvalidMoveException(String message) {
        super(message);
    }
}

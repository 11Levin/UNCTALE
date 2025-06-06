package edu.unc.comp301.exceptions;

public class InvalidMoveException extends IllegalArgumentException {
    public InvalidMoveException(String message) {
        super(message);
    }
}

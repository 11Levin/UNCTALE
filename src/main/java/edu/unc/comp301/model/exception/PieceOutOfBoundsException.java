package edu.unc.comp301.model.exception;

/**
 * Exception for when a piece outside a board's bounds is accessed
 */
public class PieceOutOfBoundsException extends IllegalArgumentException {
    public PieceOutOfBoundsException(String message) {
        super(message);
    }
}

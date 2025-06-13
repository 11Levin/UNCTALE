package edu.unc.comp301.model.exception;

public class PieceOutOfBoundsException extends IllegalArgumentException{
    public PieceOutOfBoundsException(String message) {
        super(message);
    }
}

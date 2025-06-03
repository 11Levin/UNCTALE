package edu.unc.comp301.Exceptions;

public class LevelNotLoadedException extends IllegalArgumentException {
    public LevelNotLoadedException(String message) {
        super(message);
    }
}

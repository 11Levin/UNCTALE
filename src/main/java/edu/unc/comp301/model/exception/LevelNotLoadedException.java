package edu.unc.comp301.model.exception;

public class LevelNotLoadedException extends IllegalArgumentException {
    public LevelNotLoadedException(String message) {
        super(message);
    }
}

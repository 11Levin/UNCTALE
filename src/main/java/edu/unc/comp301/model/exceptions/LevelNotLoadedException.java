package edu.unc.comp301.model.exceptions;

public class LevelNotLoadedException extends IllegalArgumentException {
    public LevelNotLoadedException(String message) {
        super(message);
    }
}

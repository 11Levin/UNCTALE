package edu.unc.comp301.model.exception;

/**
 * Exception for general level-loading errors
 */
public class LevelNotLoadedException extends IllegalArgumentException {
    public LevelNotLoadedException(String message) {
        super(message);
    }
}

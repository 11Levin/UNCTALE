package edu.unc.comp301.Exceptions;

public class InvalidBoardDimensionsException extends IllegalArgumentException{
    public InvalidBoardDimensionsException(String message) {
        super(message);
    }
}

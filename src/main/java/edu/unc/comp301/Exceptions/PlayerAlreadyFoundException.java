package edu.unc.comp301.Exceptions;

public class PlayerAlreadyFoundException extends IllegalArgumentException{
    public PlayerAlreadyFoundException(String message) {
        super(message);
    }
}

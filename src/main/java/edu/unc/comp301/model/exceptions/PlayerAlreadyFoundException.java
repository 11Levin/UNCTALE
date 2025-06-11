package edu.unc.comp301.model.exceptions;

public class PlayerAlreadyFoundException extends IllegalArgumentException{
    public PlayerAlreadyFoundException(String message) {
        super(message);
    }
}

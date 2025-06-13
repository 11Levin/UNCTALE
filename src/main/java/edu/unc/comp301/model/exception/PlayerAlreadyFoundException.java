package edu.unc.comp301.model.exception;

public class PlayerAlreadyFoundException extends IllegalArgumentException{
    public PlayerAlreadyFoundException(String message) {
        super(message);
    }
}

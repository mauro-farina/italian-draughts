package it.units.inginf.italiandraughts.exception;

public class PlayerColorException extends Exception{
    private final String message;

    public PlayerColorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PlayerColorException: invalid PlayerColor, " + message;
    }

}

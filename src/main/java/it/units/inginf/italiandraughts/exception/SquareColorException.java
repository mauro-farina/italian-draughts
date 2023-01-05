package it.units.inginf.italiandraughts.exception;

public class SquareColorException extends Exception {

    private final String message;

    public SquareColorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "SquareColorException: invalid SquareColor, " + message;
    }

}

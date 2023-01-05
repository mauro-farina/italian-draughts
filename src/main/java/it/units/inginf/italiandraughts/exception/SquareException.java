package it.units.inginf.italiandraughts.exception;

public class SquareException extends Exception {

    private final String message;

    public SquareException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "SquareException: invalid Square, " + message;
    }
}

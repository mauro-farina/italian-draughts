package it.units.inginf.italiandraughts.exception;

public class SquareContentException extends Exception {

    private final String message;

    public SquareContentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "SquareContentException: invalid SquareContent, " + message;
    }

}

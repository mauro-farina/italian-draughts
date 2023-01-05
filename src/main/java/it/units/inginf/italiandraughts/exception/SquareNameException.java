package it.units.inginf.italiandraughts.exception;

public class SquareNameException extends Exception {

    private final String message;

    public SquareNameException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "SquareNameException: invalid SquareName, " + message;
    }

}

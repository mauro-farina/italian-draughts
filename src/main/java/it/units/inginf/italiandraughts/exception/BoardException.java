package it.units.inginf.italiandraughts.exception;

public class BoardException extends Exception {

    private final String message;

    public BoardException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "BoardException: invalid Board, " + message;
    }
}

package it.units.inginf.italiandraughts.exception;

public class PieceException extends Exception {

    private final String message;

    public PieceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PieceException: invalid Piece, " + message;
    }
}

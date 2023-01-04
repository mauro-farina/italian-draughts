package it.units.inginf.italiandraughts.exception;

public class PieceColorException extends Exception {

    private String message;

    public PieceColorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PieceColorException: invalid PieceColor, " + message;
    }

}

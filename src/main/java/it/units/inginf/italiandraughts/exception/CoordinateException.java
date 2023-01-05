package it.units.inginf.italiandraughts.exception;

public class CoordinateException extends Exception {

    private final String message;

    public CoordinateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "CoordinateException: invalid Coordinate, " + message;
    }
}

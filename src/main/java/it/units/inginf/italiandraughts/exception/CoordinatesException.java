package it.units.inginf.italiandraughts.exception;

public class CoordinatesException extends Exception {

    private final String message;

    public CoordinatesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "CoordinatesException: invalid Coordinate, " + message;
    }
}

package it.units.inginf.italiandraughts.exception;

public class CommandException extends Exception {

    private final String message;

    public CommandException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "CommandException: invalid Command, " + message;
    }
}

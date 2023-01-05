package it.units.inginf.italiandraughts.exception;

public class PlayerException extends Exception {

    private final String message;

    public PlayerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PlayerNicknameException: invalid PlayerNickname, " + message;
    }
}

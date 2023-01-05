package it.units.inginf.italiandraughts.exception;

public class PlayerNicknameException extends Exception {

    private String message;

    public PlayerNicknameException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "PlayerNicknameException: invalid PlayerNickname, " + message;
    }
  
}

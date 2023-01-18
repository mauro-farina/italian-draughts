package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.exception.PlayerColorException;
import it.units.inginf.italiandraughts.exception.PlayerNicknameException;

public class Player {

    private final String nickname;
    private final PlayerColor playerColor;

    public Player(String nickname, PlayerColor playerColor) throws PlayerColorException, PlayerNicknameException {
        if(nickname == null) {
            throw new PlayerNicknameException("Player.Player() does not accept this nickname");
        } else {
            this.nickname = nickname;
        }
        if((playerColor == PlayerColor.WHITE) || (playerColor == PlayerColor.BLACK)) {
            this.playerColor = playerColor;
        } else {
            throw new PlayerColorException("Player.Player() does not accept this PlayerColor");
        }
    }

    public String getNickname() {
        return this.nickname;
    }

    public PlayerColor getColor() {
        return this.playerColor;
    }

}

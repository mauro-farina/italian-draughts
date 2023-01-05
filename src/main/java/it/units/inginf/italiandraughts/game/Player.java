package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.exception.PlayerColorException;

public class Player {

    private String nickname;
    private PlayerColor playerColor;

    public Player(String nickname, PlayerColor playerColor) throws Exception {
        if(nickname == null) {
            throw new Exception("The player's nickname is not valid");
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

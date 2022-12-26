package it.units.inginf.italiandraughts;

public class Player {

    private String nickname;

    private PlayerColor color;

    public Player(String nickname, PlayerColor color) throws Exception {
        if(nickname == null) {
            throw new Exception("The player's nickname is not valid");
        } else {
            this.nickname = nickname;
        }
        if(color == null) {
            throw new Exception("The player's color is not valid");
        } else {
            this.color = color;
        }
    }

    public String getNickname() {
        return this.nickname;
    }

    public PlayerColor getColor() {
        return this.color;
    }

}

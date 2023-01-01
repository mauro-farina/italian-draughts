package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;

public class Main {

    public static void main(String[] args) throws Exception {
        new Game(
                new Player("player one", PlayerColor.WHITE),
                new Player("player two", PlayerColor.BLACK)
        ).start();
    }
}

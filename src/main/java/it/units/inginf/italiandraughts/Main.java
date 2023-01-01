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

        // doesn't check if the square actually has a piece (ex: A4 to B5, A4 could be empty)
        // despite exception thrown on "A2 to A3", piece actually moves to A3
        // turns are not alternating
    }
}

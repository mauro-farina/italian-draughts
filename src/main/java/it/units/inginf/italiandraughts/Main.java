package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.io.InputReader;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.exception.PlayerColorException;

public class Main {
    
    private final static InputReader inputReader = new CommandLineInputReader();

    public static void main(String[] args) throws Exception {
        new Game(
                new Player(readName(PlayerColor.WHITE), PlayerColor.WHITE),
                new Player(readName(PlayerColor.BLACK), PlayerColor.BLACK)
        ).start();
    }

    private static String readName(PlayerColor color) throws PlayerColorException {
        while(true) {
            if(color == PlayerColor.WHITE) {
                System.out.println("Type player one's name");
            } else if (color == PlayerColor.BLACK) {
                System.out.println("Type player two's name");
            } else {
                throw new PlayerColorException("Main.main() does not accept this PlayerColor");
            }
            String name = inputReader.readInput();
            if((!name.equals("")) && (name.charAt(0) != ' ')) {
                return name;
            } else {
                System.out.println("Invalid name, please try again");
            }
        }
    }
}

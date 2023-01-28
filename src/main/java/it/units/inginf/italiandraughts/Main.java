package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.exception.PlayerColorException;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;
import it.units.inginf.italiandraughts.io.InputReader;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.io.OutputPrinter;


public class Main {
    private final static InputReader inputReader = new CommandLineInputReader();
    private final static OutputPrinter outputPrinter = new CommandLineOutputPrinter();

    public static void main(String[] args) {
        try{
            new Game(
                    new Player(readName(PlayerColor.WHITE), PlayerColor.WHITE),
                    new Player(readName(PlayerColor.BLACK), PlayerColor.BLACK),
                    inputReader, outputPrinter).start();
        } catch(Exception e) {
            outputPrinter.print(e.getMessage());
        }
    }

    private static String readName(PlayerColor playerColor) throws PlayerColorException {
        while(true) {
            if(playerColor == PlayerColor.WHITE) {
                outputPrinter.print("Type player one's name");
            } else if (playerColor == PlayerColor.BLACK) {
                outputPrinter.print("Type player two's name");
            } else {
                throw new PlayerColorException("Main.main() does not accept this PlayerColor");
            }
            String name = inputReader.readInput();
            if((!name.equals("")) && (name.charAt(0) != ' ')) {
                return name;
            } else {
                outputPrinter.print("Invalid name, please try again");
            }
        }
    }
}

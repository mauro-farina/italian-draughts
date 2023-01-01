package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareName;
import it.units.inginf.italiandraughts.commands.*;

public class CommandParser {

    // static ?
    // probably a command parser should not require board
    public static Command parseCommand(String commandAsString) throws Exception {
        String[] commandTokens = commandAsString.trim().split(" "); //with trim
        if(commandTokens.length == 1) {
            if(commandTokens[0].equalsIgnoreCase("help")){
                return new CommandHelp();
            }
            if(commandTokens[0].equalsIgnoreCase("surrender")
                    || commandTokens[0].equalsIgnoreCase("sur")) {
                return new CommandSurrender();
            }
        }
        if(commandTokens.length == 3) {
            SquareCoordinates firstSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[0]));
            SquareCoordinates secondSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[2]));
            if(commandTokens[1].equalsIgnoreCase("to")) {
                return new CommandTo(firstSquareCoordinates, secondSquareCoordinates);
            }
            if(commandTokens[1].equalsIgnoreCase("capture")
                    || commandTokens[1].equalsIgnoreCase("capt")
                    || commandTokens[1].equalsIgnoreCase("cap")) {
                return new CommandCapture(firstSquareCoordinates, secondSquareCoordinates);
            }
        }
        throw new Exception("Unknown command. Type HELP to see the available commands.");
    }
}

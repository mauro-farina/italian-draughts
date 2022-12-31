package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.Utils;
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
            if(commandTokens[1].equalsIgnoreCase("to")) {
                int[] fromCoordinates = {
                        Utils.convertToCoordinates(commandTokens[0])[0],
                        Utils.convertToCoordinates(commandTokens[0])[1]
                };
                int[] toCoordinates = {
                        Utils.convertToCoordinates(commandTokens[2])[0],
                        Utils.convertToCoordinates(commandTokens[2])[1]
                };
                return new CommandTo(fromCoordinates, toCoordinates);
            }
            if(commandTokens[1].equalsIgnoreCase("capture")
                    || commandTokens[1].equalsIgnoreCase("capt")
                    || commandTokens[1].equalsIgnoreCase("cap")) {
                int[] fromCoordinates = {
                        Utils.convertToCoordinates(commandTokens[0])[0],
                        Utils.convertToCoordinates(commandTokens[0])[1]
                };
                int[] pieceToCaptureCoordinates = {
                        Utils.convertToCoordinates(commandTokens[2])[0],
                        Utils.convertToCoordinates(commandTokens[2])[1]
                };
                return new CommandCapture(fromCoordinates, pieceToCaptureCoordinates);
            }
        }
        throw new Exception("Unknown command. Type HELP to see the available commands.");
    }
}

package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareName;
import it.units.inginf.italiandraughts.commands.*;
import it.units.inginf.italiandraughts.exception.CommandException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareNameException;

public class CommandParser {

    // static ?
    // probably a command parser should not require board
    public static Command parseCommand(String commandAsString) throws CommandException, CoordinatesException, SquareNameException {
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
        throw new CommandException("CommandParser.parseCommand() does not accept this unknown command. " +
                "\n Type HELP to see the available commands.");
    }

    public static CommandTo parseCommandTo(String commandAsString) throws CommandException, SquareNameException, CoordinatesException{
        String[] commandTokens = commandAsString.trim().split(" "); //with trim
        if(commandTokens.length == 3) {
            SquareCoordinates firstSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[0]));
            SquareCoordinates secondSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[2]));
            if (commandTokens[1].equalsIgnoreCase("to")) {
                return new CommandTo(firstSquareCoordinates, secondSquareCoordinates);
            }
        }
        throw new CommandException("CommandParser.parseCommandTo() does not accept this unknown commandTo. " +
                "\n Type HELP to see the available commands.");
    }

    public static CommandCapture parseCommandCapture(String commandAsString) throws CommandException, SquareNameException, CoordinatesException{
        String[] commandTokens = commandAsString.trim().split(" "); //with trim
        if(commandTokens.length == 3) {
            SquareCoordinates firstSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[0]));
            SquareCoordinates secondSquareCoordinates = new SquareCoordinates(new SquareName(commandTokens[2]));
            if (commandTokens[1].equalsIgnoreCase("capture")
                    || commandTokens[1].equalsIgnoreCase("capt")
                    || commandTokens[1].equalsIgnoreCase("cap")) {
                return new CommandCapture(firstSquareCoordinates, secondSquareCoordinates);
            }
        }
        throw new CommandException("CommandParser.parseCommandCapture() does not accept this unknown " +
                "commandCapture. \n Type HELP to see the available commands.");
    }
}

package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.commands.Command;
import it.units.inginf.italiandraughts.commands.CommandTo;
import it.units.inginf.italiandraughts.commands.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    void checkCommandTypeHelp() {
        Command command;
        try {
            command = CommandParser.parseCommand("help");
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(command.getCommandType(), CommandType.HELP);
    }

    @Test
    void checkCommandTypeSurrender() {
        Command command;
        try {
            command = CommandParser.parseCommand("sur");
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(command.getCommandType(), CommandType.SURRENDER);
    }

    @Test
    void checkCoordinatesStartingSquare() {
        Command command;
        SquareCoordinates expectedCoordinates;
        try {
            expectedCoordinates = new SquareCoordinates(0, 0); // A1
            command = CommandParser.parseCommand("A1 to B2");
            SquareCoordinates commandStartingSquareCoordinates = ((CommandTo)command).getFromCoordinates();
            if(expectedCoordinates.getColumn() != commandStartingSquareCoordinates.getColumn()
                    || expectedCoordinates.getRow() != commandStartingSquareCoordinates.getRow())
                fail();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkCoordinatesArrivalSquare() {
        Command command;
        SquareCoordinates expectedCoordinates;
        try {
            expectedCoordinates = new SquareCoordinates(1, 1); // B2
            command = CommandParser.parseCommand("A1 to B2");
            SquareCoordinates commandMoveToSquareCoordinates = ((CommandTo)command).getToCoordinates();
            if(expectedCoordinates.getColumn() != commandMoveToSquareCoordinates.getColumn()
                    || expectedCoordinates.getRow() != commandMoveToSquareCoordinates.getRow())
                fail();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

}

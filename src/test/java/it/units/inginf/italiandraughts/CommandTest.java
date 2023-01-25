package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.*;
import it.units.inginf.italiandraughts.commands.Command;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.commands.CommandTo;
import it.units.inginf.italiandraughts.commands.CommandType;
import it.units.inginf.italiandraughts.exception.*;
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

    @Test
    void checkIsValidWhenIsNot() {
        CommandCapture commandCapture;
        try {
            commandCapture = new CommandCapture(
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            try {
                assertFalse(commandCapture.isValid(new Board()));
            } catch (CommandException e) {
                assertTrue(true);
            } catch(BoardException | SquareException e) {
                fail();
            }

        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkIsValidWhenTrue() {
        CommandCapture commandCapture;
        try {
            Board board = new Board();
            commandCapture = new CommandCapture( // A6 CAPTURE B5
                    new SquareCoordinates(0, 5),
                    new SquareCoordinates(1, 4));
            board.getWhitePieces().add(
                    new Man(
                        PieceColor.WHITE,
                        board.getSquare(new SquareCoordinates(1, 4))) // B5
            );
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.WHITE_MAN);
            try {
                assertTrue(commandCapture.isValid(board));
            } catch (CommandException | BoardException | SquareException e) {
                fail();
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

}

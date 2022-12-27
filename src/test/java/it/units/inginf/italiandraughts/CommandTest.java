package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    void checkCommandTypeHelp() {
        Command command;
        try {
            command = new Command("help");
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
            command = new Command("sur");
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(command.getCommandType(), CommandType.SURRENDER);
    }

    @Test
    void checkCoordinatesStartingSquare() {
        Command command;
        try {
            command = new Command("A1 to B2");
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        int[] expectedCoordinates = {0, 0};
        int[] coordinates = command.getCoordinatesStartingSquare();
        boolean test = true;
        for(int i = 0; i < 2; i++) {
            if(coordinates[i] != expectedCoordinates[i]) {
                test = false;
            }
        }
        assertTrue(test);
    }

    @Test
    void checkCoordinatesArrivalSquare() {
        Command command;
        try {
            command = new Command("A1 to B2");
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        int[] expectedCoordinates = {1, 1};
        int[] coordinates = command.getCoordinatesArrivalSquare();
        boolean test = true;
        for(int i = 0; i < 2; i++) {
            if(coordinates[i] != expectedCoordinates[i]) {
                test = false;
            }
        }
        assertTrue(test);
    }

}

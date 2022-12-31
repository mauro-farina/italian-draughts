package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CoordinateSystemTest {

    @Test
    void createSquareCoordinatesFromSquareName() {
        char column = 'B', row = '3';
        SquareCoordinates squareCoordinates;
        SquareName squareName;
        try {
            squareName = new SquareName(column, row);
        } catch (Exception e) {
            fail("coordinates should be valid");
            throw new RuntimeException(e);
        }
        try {
            squareCoordinates = new SquareCoordinates(squareName);
        } catch (Exception e) {
            fail("squareName should be valid");
            throw new RuntimeException(e);
        }
        assertEquals("B3", squareName.toString());
        assertEquals("(1,2)", squareCoordinates.toString());
    }

    @Test
    void checkCoordinatesFromSquare() {
        Square square;
        try {
            square = new Square(2, 4); // C5
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals("(2,4)", square.getSquareCoordinates().toString());
        assertEquals("C5", square.getSquareName().toString());
    }
}

package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareColor;
import it.units.inginf.italiandraughts.board.SquareContent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class SquareTest {

    @Test
    void checkSquareColor() {
        int x=0, y=0;
        Square square;
        try {
            square = new Square(x, y);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        SquareColor squareColor = square.getSquareColor();
        assertEquals(SquareColor.WHITE, squareColor);
    }

    @Test
    void checkSquareCoordinateX(){
        int x=0, y=0;
        Square square;
        try {
            square = new Square(x, y);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        // Assert
        assertEquals('A', square.getSquareName().getColumn());
    }

    @Test
    void checkSquareCoordinateY(){
        int x=2, y=5;
        Square square;
        try {
            square = new Square(x, y);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        // Assert
        assertEquals('6', square.getSquareName().getRow());
    }

    @Test
    void checkSquareContent(){
        int x=2, y=5;
        Square square;
        try {
            square = new Square(x, y);
            square.setSquareContent(SquareContent.BLACK_MAN);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(SquareContent.BLACK_MAN, square.getSquareContent());
    }

    @Test
    void checkSquareIsFree(){
        int x=2, y=5;
        Square square;
        try {
            square = new Square(x, y);
            square.setSquareContent(SquareContent.BLACK_MAN);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(square.isFree());
    }

}

package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    @Test
    void checkManColor() {
        King king;
        try{
            Square square = new Square(2, 5);
            king = new King(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        PieceColor expectedColor = PieceColor.BLACK;
        PieceColor actualColor = king.getColor();
        assertEquals(expectedColor, actualColor);
    }

    @Test
    void checkManSquare(){
        King king;
        Square square;
        try{
            square = new Square(2, 5);
            king = new King(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        Square actualSquare = king.getSquare();
        assertEquals(square, actualSquare);
    }

    @Test
    void checkIsMan() {
        King king;
        try{
            Square square = new Square(2, 5);
            king = new King(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(king.isMan());
    }

    @Test
    void checkIsKing() {
        King king;
        try{
            Square square = new Square(2, 5);
            king = new King(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(king.isKing());
    }

    @Test
    void checkSetSquare() {
        King king;
        Square square;
        Square newSquare;
        try{
            square = new Square(2, 5);
            king = new King(PieceColor.BLACK, square);
            newSquare = new Square(4, 5);
            king.setSquare(newSquare);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        Square actualSquare = king.getSquare();
        assertEquals(newSquare, actualSquare);
    }

}

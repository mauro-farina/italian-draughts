package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ManTest {

    @Test
    void checkManColor() {
        Man man;
        try{
            Square square = new Square(2, 5);
            man = new Man(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        PieceColor expectedColor = PieceColor.BLACK;
        PieceColor actualColor = man.getColor();
        assertEquals(expectedColor, actualColor);
    }

    @Test
    void checkManSquare(){
        Man man;
        Square square;
        try{
            square = new Square(2, 5);
            man = new Man(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        Square actualSquare = man.getSquare();
        assertEquals(square, actualSquare);
    }

    @Test
    void checkIsMan() {
        Man man;
        try{
            Square square = new Square(2, 5);
            man = new Man(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(man.isMan());
    }

    @Test
    void checkIsKing() {
        Man man;
        try{
            Square square = new Square(2, 5);
            man = new Man(PieceColor.BLACK, square);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(man.isKing());
    }

    @Test
    void checkSetSquare() {
        Man man;
        Square square;
        Square newSquare;
        try{
            square = new Square(2, 5);
            man = new Man(PieceColor.BLACK, square);
            newSquare = new Square(4, 5);
            man.setSquare(newSquare);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        Square actualSquare = man.getSquare();
        assertEquals(newSquare, actualSquare);
    }

}

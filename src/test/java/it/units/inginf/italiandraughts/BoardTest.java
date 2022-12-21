package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {

    @Test
    void checkGetNumberOfPieces() {
        int numberOfPieces;
        try {
            Board board = new Board();
            numberOfPieces = board.getNumberOfPieces(PieceColor.BLACK);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(numberOfPieces, 12);
    }

    @Test
    void checkGetLastRow() {
        Square[] lastRow;
        Square[] expectedLastRow;
        try {
            Board board = new Board();
            Square[][] boardSquares = board.getBoardSquares();
            lastRow = board.getLastRow(PieceColor.WHITE);
            expectedLastRow = boardSquares[7];
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(lastRow, expectedLastRow);
    }
    
}

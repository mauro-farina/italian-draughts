package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.PieceColor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

class BoardTest {

    @Test
    void checkNumberWhitePieces() {
        int numberOfWhitePieces;
        try {
            Board board = new Board();
            BoardUtils.removePiece(board, board.getWhitePieces().get(2));
            numberOfWhitePieces = board.getWhitePieces().size();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(11, numberOfWhitePieces);
    }

    @Test
    void checkNumberBlackPieces() {
        int numberOfBlackPieces;
        try {
            Board board = new Board();
            BoardUtils.removePiece(board, board.getBlackPieces().get(3));
            numberOfBlackPieces = board.getBlackPieces().size();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(11, numberOfBlackPieces);
    }
    
    @Test
    void checkGetLastRow() {
        Square[] lastRow;
        Square[] expectedLastRow;
        try {
            Board board = new Board();
            Square[][] boardSquares = board.getBoardSquares();
            lastRow = board.getLastRow(PieceColor.WHITE);
            expectedLastRow = new Square[8];
            for(int i = 0; i < 8; i++) {
                expectedLastRow[i] = boardSquares[i][7];
                if (expectedLastRow[i] != lastRow[i]) {
                    fail();
                }
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkReachableSquaresWithMan(){
        try {
            Board board = new Board();
            SquareCoordinates squareCoordinatesB3 = new SquareCoordinates(1, 2);
            Piece piece = new Man(PieceColor.WHITE, board.getSquare(squareCoordinatesB3)); // piece in B3
            List<Square> reachableSquares = board.getReachableSquares(piece);
            SquareCoordinates squareCoordinatesA4 = new SquareCoordinates(0, 3);
            if(!reachableSquares.contains(board.getSquare(squareCoordinatesA4))) // if A4 is NOT in the list
                fail("A reachable square is not considered so");
            SquareCoordinates squareCoordinatesC4 = new SquareCoordinates(2, 3);
            if(!reachableSquares.contains(board.getSquare(squareCoordinatesC4))) // if C4 is NOT in the list
                fail("A reachable square is not considered so");
            assertEquals(2, reachableSquares.size(), "some non-reachable squares are considered reachable");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    void checkReachableSquaresWithKing(){
        try {
            Board board = new Board();
            SquareCoordinates squareCoordinatesH7 = new SquareCoordinates(7, 6);
            Piece piece = new King(PieceColor.BLACK, board.getSquare(squareCoordinatesH7)); // piece in H7
            List<Square> reachableSquares = board.getReachableSquares(piece);
            SquareCoordinates squareCoordinatesG8 = new SquareCoordinates(6, 7);
            if(!reachableSquares.contains(board.getSquare(squareCoordinatesG8))) // if G8 is NOT in the list
                fail("A reachable square is not considered so");
            SquareCoordinates squareCoordinatesG6 = new SquareCoordinates(6, 5);
            if(!reachableSquares.contains(board.getSquare(squareCoordinatesG6))) // if G6 is NOT in the list
                fail("A reachable square is not considered so");
            assertEquals(2, reachableSquares.size(), "some non-reachable squares are considered reachable");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    void testToStringRepresentation() {
        try {
            Board board = new Board();
            String expectedBoard = "8\t[b]\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t" + System.lineSeparator() +
                    "7\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t[b]\t" + System.lineSeparator() +
                    "6\t[b]\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t[b]\t[ ]\t" + System.lineSeparator() +
                    "5\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t" + System.lineSeparator() +
                    "4\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t[ ]\t" + System.lineSeparator() +
                    "3\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t[w]\t" + System.lineSeparator() +
                    "2\t[w]\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t" + System.lineSeparator() +
                    "1\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t[w]\t[ ]\t[w]\t" + System.lineSeparator() +
                    "\t A \t B \t C \t D \t E \t F \t G \t H";
            assertEquals(expectedBoard, board.toString());
        } catch (Exception e) {
            fail();
        }
    }

}

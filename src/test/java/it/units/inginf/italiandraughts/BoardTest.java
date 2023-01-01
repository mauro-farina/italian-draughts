package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
public class BoardTest {

    @Test
    void checkNumberWhitePieces() {
        int numberOfWhitePieces;
        try {
            Board board = new Board();
            board.removePiece(PieceColor.WHITE, 2);
            numberOfWhitePieces = board.getWhitePieces().size();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(numberOfWhitePieces, 11);
    }

    @Test
    void checkNumberBlackPieces() {
        int numberOfBlackPieces;
        try {
            Board board = new Board();
            board.removePiece(PieceColor.BLACK, 3);
            numberOfBlackPieces = board.getBlackPieces().size();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(numberOfBlackPieces, 11);
    }

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

    @Test
    void checkManBecomesKing() {
        List<Piece> whitePieces;
        try {
            Board board = new Board();
            board.manBecomesKing(PieceColor.WHITE, 2);
            whitePieces = board.getWhitePieces();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(whitePieces.get(whitePieces.size() - 1).isKing());
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
    void checkResearchPiece() {
        Board board;
        try {
            board = new Board();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        int i;
        try {
            for(i= 0; i < board.getWhitePieces().size() - 1; i++) {
                if(board.getWhitePieces().get(i).getSquare() == board.getSquare(new SquareCoordinates(0,1))) {
                    break;
                }
            }
            assertEquals(
                    board.researchPiece(board.getSquare(new SquareCoordinates(0,1))),
                    board.getWhitePieces().get(i)
            );
        } catch (Exception e){
            fail();
            throw new RuntimeException();
        }
    }

}

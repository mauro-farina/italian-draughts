package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {

    @Test
    void checkNumberWhitePieces() {
        int numberOfWhitePieces;
        try {
            Board board = new Board();
            board.decrementNumberWhitePieces();
            numberOfWhitePieces = board.getNumberWhitePieces();
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
            board.decrementNumberBlackPieces();
            numberOfBlackPieces = board.getNumberBlackPieces();
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
    void checkReachableSquaresWithMan(){
        try {
            Board board = new Board();
            Piece piece = new Man(PieceColor.WHITE, board.getSquare(1,2)); // piece in B3*
            List<Square> reachableSquares = board.getReachableSquares(piece);
            if(!reachableSquares.contains(board.getSquare(0,3))) // if A4 is NOT in the list
                fail("A reachable square is not considered so");
            if(!reachableSquares.contains(board.getSquare(2,3))) // if C4 is NOT in the list
                fail("A reachable square is not considered so");
            if(reachableSquares.contains(board.getSquare(0,1)))  // if A2 IS in the list
                fail("A non-reachable square is considered reachable");
            if(reachableSquares.contains(board.getSquare(2,1)))  // if C2 IS in the list
                fail("A non-reachable square is considered reachable");
            if(reachableSquares.contains(board.getSquare(1,3))) // if B4 IS in the list
                fail("A non-reachable square is considered reachable");
            if(reachableSquares.contains(board.getSquare(2,2))) // if C3 IS in the list
                fail("A non-reachable square is considered reachable");
            assertEquals(2, reachableSquares.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    void checkReachableSquaresWithKing(){
        try {
            Board board = new Board();
            Piece piece = new King(PieceColor.BLACK, board.getSquare(7,6)); // piece in H7
            List<Square> reachableSquares = board.getReachableSquares(piece);
            if(!reachableSquares.contains(board.getSquare(6,7))) // if G8 is NOT in the list
                fail("A reachable square is not considered so");
            if(!reachableSquares.contains(board.getSquare(6,5))) // if G6 is NOT in the list
                fail("A reachable square is not considered so");
            assertEquals(2, reachableSquares.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}

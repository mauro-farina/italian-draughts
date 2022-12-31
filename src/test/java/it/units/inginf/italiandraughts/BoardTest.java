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
    
    @Test
    void checkResearchPiece() {
        Board board;
        Square square;
        try {
            board = new Board();
            square = new Square(0,0);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        int i;
        for(i= 0; i < board.getWhitePieces().size() - 1; i++) {
            if(board.getWhitePieces().get(i).getSquare() == board.getSquare(0,1)) {
                break;
            }
        }
        assertEquals(board.researchPiece(board.getSquare(0,1)), board.getWhitePieces().get(i));
    }

}

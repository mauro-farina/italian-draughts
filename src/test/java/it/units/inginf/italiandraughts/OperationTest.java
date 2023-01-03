package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.*;
import it.units.inginf.italiandraughts.board.operation.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperationTest {

    @Test
    void checkOperationType() {
        Operation operation;
        try {
            Board board = new Board();
            operation = new OperationRemovePiece(board, PieceColor.WHITE, 1);
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(operation.getOperationType(), OperationType.REMOVE_PIECE);
    }

    @Test
    void checkOperationRemovePiece() {
        Board board;
        try {
            board = new Board();
            new OperationRemovePiece(board, PieceColor.WHITE, 1).run();
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(board.getWhitePieces().size(), 11);
    }

    @Test
    void checkOperationManBecomesKing() {
        Board board;
        try {
            board = new Board();
            new OperationManBecomesKing(board, PieceColor.WHITE, 1).run();
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(board.getWhitePieces().get(board.getWhitePieces().size() - 1).isKing());
    }
    
    @Test
    void checkOperationResearchPiece() {
        Piece piece;
        Board board;
        try {
            board = new Board();
            Square square = board.getSquare(new SquareCoordinates(new SquareName("A2")));
            piece = new OperationResearchPiece(board, square).getPieceSought();
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(piece, board.getWhitePieces().get(4));
    }
    
}

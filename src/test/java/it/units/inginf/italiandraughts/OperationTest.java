package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.operation.OperationType;
import it.units.inginf.italiandraughts.board.operation.Operation;
import it.units.inginf.italiandraughts.board.operation.OperationRemovePiece;
import it.units.inginf.italiandraughts.board.operation.OperationManBecomesKing;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;

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
}

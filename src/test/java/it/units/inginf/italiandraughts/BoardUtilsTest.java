package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardUtilsTest {

    @Test
    void checkRemovePiece() {
        Board board;
        try {
            board = new Board();
            Piece piece = board.getWhitePieces().get(0);
            BoardUtils.removePiece(board, piece);
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(board.getWhitePieces().size(), 11);
    }

    @Test
    void checkToCrown() {
        Board board;
        try {
            board = new Board();
            Piece piece = board.getWhitePieces().get(0);
            BoardUtils.toCrown(board, piece);
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(board.getWhitePieces().get(board.getWhitePieces().size() - 1).isKing());
    }

}

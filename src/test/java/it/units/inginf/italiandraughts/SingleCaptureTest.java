package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.SingleCapture;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class SingleCaptureTest {

    @Test
    void checkIsValid() {
        SingleCapture singleCapture;
        try {
            singleCapture = new SingleCapture(new Board(),
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            assertFalse(singleCapture.isValid());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkPieceOnFromCoordinatesIsKing() {
        SingleCapture singleCapture;
        try {
            singleCapture = new SingleCapture(new Board(),
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            assertFalse(singleCapture.pieceOnFromCoordinatesIsKing());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkPieceOnToCoordinatesIsKing() {
        try {
            SingleCapture singleCapture = new SingleCapture(new Board(),
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            assertFalse(singleCapture.pieceOnToCoordinatesIsKing());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkManNotCaptureKing() {
        try {
            Board board = new Board();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            board.getSquare(new SquareCoordinates(0, 3)).setSquareContent(SquareContent.WHITE_MAN);
            Man whiteMan = new Man(PieceColor.WHITE, board.getSquare(new SquareCoordinates(0, 3)));
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_KING);
            King blackKing = new King(PieceColor.BLACK, board.getSquare(new SquareCoordinates(1, 4)));
            assertFalse(new SingleCapture(board,
                    new SquareCoordinates(0, 3),
                    new SquareCoordinates(1, 4)).isValid());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
}

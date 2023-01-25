package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.commands.ExecutableCommandCapture;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ExecutableCommandCaptureTest {

    @Test
    void checkIsValid() {
        ExecutableCommandCapture singleCapture;
        try {
            singleCapture = new ExecutableCommandCapture(new Board(),
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
        ExecutableCommandCapture singleCapture;
        try {
            singleCapture = new ExecutableCommandCapture(new Board(),
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            assertFalse(singleCapture.isCapturingPieceKing());
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
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_KING);
            assertFalse(new ExecutableCommandCapture(board,
                    new SquareCoordinates(0, 3),
                    new SquareCoordinates(1, 4)).isValid());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
}

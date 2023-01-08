package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.SingleCapture;
import it.units.inginf.italiandraughts.board.Board;
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
        SingleCapture singleCapture;
        try {
            singleCapture = new SingleCapture(new Board(),
                    new SquareCoordinates(1, 2),
                    new SquareCoordinates(2, 3));
            assertFalse(singleCapture.pieceOnToCoordinatesIsKing());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
}

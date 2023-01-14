package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.ObligatoryCapture;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObligatoryCaptureTest {

    @Test
    void checkKingCaptureMan() {
        try {
            Board board = new Board();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 5)).setSquareContent(SquareContent.WHITE_KING);
            King whiteKing = new King(PieceColor.WHITE, board.getSquare(new SquareCoordinates(0, 5)));
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            Man blackMan = new Man(PieceColor.BLACK, board.getSquare(new SquareCoordinates(1, 4)));
            board.getWhitePieces().add(whiteKing);
            board.getBlackPieces().add(blackMan);
            assertEquals(ObligatoryCapture.getObligatoryCapture(board, blackMan).size(), 1);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void checkTripleCapture() {
        try {
            Board board = new Board();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(3, 6)).setSquareContent(SquareContent.WHITE_MAN);
            Man whiteMan1 = new Man(PieceColor.WHITE, board.getSquare(new SquareCoordinates(3, 6)));
            board.getSquare(new SquareCoordinates(3, 4)).setSquareContent(SquareContent.WHITE_MAN);
            Man whiteMan2 = new Man(PieceColor.WHITE, board.getSquare(new SquareCoordinates(3, 4)));
            board.getSquare(new SquareCoordinates(1, 2)).setSquareContent(SquareContent.WHITE_MAN);
            Man whiteMan3 = new Man(PieceColor.WHITE, board.getSquare(new SquareCoordinates(1, 2)));
            board.getSquare(new SquareCoordinates(2, 7)).setSquareContent(SquareContent.BLACK_MAN);
            Man blackMan = new Man(PieceColor.BLACK, board.getSquare(new SquareCoordinates(2, 7)));
            board.getWhitePieces().add(whiteMan1);
            board.getWhitePieces().add(whiteMan2);
            board.getWhitePieces().add(whiteMan3);
            board.getBlackPieces().add(blackMan);
            assertEquals(ObligatoryCapture.getObligatoryCapture(board, whiteMan1).size(), 3);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
    
}

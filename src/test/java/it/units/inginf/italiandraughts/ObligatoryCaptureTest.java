package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.ObligatoryCapture;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.game.Player;
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
            Game game = new Game(new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK));
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 5)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(0, 5))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(1, 4))));
            assertEquals(ObligatoryCapture.getObligatoryCaptureList(game).size(), 1);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkTripleCapture() {
        try {
            Game game = new Game(new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK));
            game.initGame();
            game.changeTurn();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(3, 6)).setSquareContent(SquareContent.WHITE_MAN);
            board.getSquare(new SquareCoordinates(3, 4)).setSquareContent(SquareContent.WHITE_MAN);
            board.getSquare(new SquareCoordinates(1, 2)).setSquareContent(SquareContent.WHITE_MAN);
            board.getSquare(new SquareCoordinates(2, 7)).setSquareContent(SquareContent.BLACK_MAN);
            board.getWhitePieces().add(new Man(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(3, 6))));
            board.getWhitePieces().add(new Man(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(3, 4))));
            board.getWhitePieces().add(new Man(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(1, 2))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(2, 7))));
            assertEquals(ObligatoryCapture.getObligatoryCaptureList(game).size(), 3);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
}

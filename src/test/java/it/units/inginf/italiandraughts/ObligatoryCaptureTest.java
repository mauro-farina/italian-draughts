package it.units.inginf.italiandraughts;


import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.game.ObligatoryCapture;
import it.units.inginf.italiandraughts.game.CommandCaptureList;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.SquareCoordinates;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ObligatoryCaptureTest {
    @Test
    void checkKingCaptureMan() {
        try {
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 5)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(0, 5))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(1, 4))));
            assertEquals(1, ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn()).get(0).size());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkTripleCapture() {
        try {
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            game.changeTurn();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
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
            assertEquals(3, ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn()).get(0).size());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkCaptureWithKing() {
        try {
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 3)).setSquareContent(SquareContent.WHITE_MAN);
            board.getSquare(new SquareCoordinates(6, 3)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getSquare(new SquareCoordinates(5, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getWhitePieces().add(new Man(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(0, 3))));
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(6, 3))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(1, 4))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(5, 4))));
            List<CommandCaptureList> obligatoryCaptureList = ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn());
            Piece piece = BoardUtils.findPiece(board,
                    board.getSquare(obligatoryCaptureList.get(0).get(0).getFromCoordinates()));
            if (piece != null) {
                assertTrue(piece.isKing());
            } else {
                fail();
                throw new Exception();
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkCaptureOfMostKing() {
        try {
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 3)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(6, 3)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getSquare(new SquareCoordinates(5, 4)).setSquareContent(SquareContent.BLACK_KING);
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(0, 3))));
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(6, 3))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(1, 4))));
            board.getBlackPieces().add(new King(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(5, 4))));
            List<CommandCaptureList> obligatoryCaptureList = ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn());
            Piece piece = BoardUtils.findPiece(board,
                    board.getSquare(obligatoryCaptureList.get(0).get(0).getPieceToCaptureCoordinates()));
            if (piece != null) {
                assertTrue(piece.isKing());
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkCaptureWithCloserKing() {
        try {
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(0, 3)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(7, 2)).setSquareContent(SquareContent.WHITE_KING);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.BLACK_MAN);
            board.getSquare(new SquareCoordinates(3, 6)).setSquareContent(SquareContent.BLACK_KING);
            board.getSquare(new SquareCoordinates(6, 3)).setSquareContent(SquareContent.BLACK_KING);
            board.getSquare(new SquareCoordinates(4, 3)).setSquareContent(SquareContent.BLACK_MAN);
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(0, 3))));
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(7, 2))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(1, 4))));
            board.getBlackPieces().add(new King(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(6, 3))));
            board.getBlackPieces().add(new King(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(3, 6))));
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(4, 3))));
            List<CommandCaptureList> obligatoryCaptureList = ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn());
            assertEquals("(7,2)", obligatoryCaptureList.get(0).get(0).getFromCoordinates().toString());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
    
     @Test
    void checkManDoNotCaptureKing() {
        try{
            Game game = new Game(
                    new Player("1", PlayerColor.WHITE),
                    new Player("2", PlayerColor.BLACK),
                    () -> "",
                    out -> {});
            game.initGame();
            Board board = game.getBoard();
            board.getWhitePieces().clear();
            board.getBlackPieces().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board.getSquare(new SquareCoordinates(j, i)).setSquareContent(SquareContent.EMPTY);
                }
            }
            board.getSquare(new SquareCoordinates(2, 5)).setSquareContent(SquareContent.BLACK_MAN);
            board.getSquare(new SquareCoordinates(1, 4)).setSquareContent(SquareContent.WHITE_KING);
            board.getBlackPieces().add(new Man(PieceColor.BLACK,
                    board.getSquare(new SquareCoordinates(2, 5))));
            board.getWhitePieces().add(new King(PieceColor.WHITE,
                    board.getSquare(new SquareCoordinates(1, 4))));
            game.changeTurn();
            //now is the turn of player 2, a white king is near a black man, but a man can not capture a king
            List<CommandCaptureList> obligatoryCaptureList = ObligatoryCapture.getObligatoryCaptureListOptions(board, game.getCurrentTurn());
            assertEquals(0, obligatoryCaptureList.size());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }
    
}

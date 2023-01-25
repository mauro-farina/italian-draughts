package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GameTest {

    @Test
    void checkPlayer1() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
            new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getCurrentTurn(), game.getPlayer1());
    }

    @Test
    void checkPlayer2() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.changeTurn();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getCurrentTurn(), game.getPlayer2());
    }

    @Test
    void checkTurnCounter() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.incrementTurnCounter();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getTurnCounter(), 2);
    }

    @Test
    void victoryConditionIsTrue() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            for(int i = 11; i >= 0; i--) {
                BoardUtils.removePiece(game.getBoard(), game.getBoard().getWhitePieces().get(i));
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(game.checkVictoryCondition());
    }

    @Test
    void victoryConditionIsFalse() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(game.checkVictoryCondition());
    }

    @Test
    void checkWinnerPlayer() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.setWinnerPlayer(game.getPlayer1());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getWinnerPlayer(), game.getPlayer1());
    }

    @Test
    void checkDrawConditionIsTrue() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.getBoard().getWhitePieces().clear();
            game.getBoard().getBlackPieces().clear();
            for(short i = 0; i < 8; i++) {
                for(short j = 0; j < 8; j++) {
                    game.getBoard().getSquare(new SquareCoordinates(i, j))
                            .setSquareContent(SquareContent.EMPTY);
                }
            }
            game.getBoard().getSquare(new SquareCoordinates(1, 0))
                    .setSquareContent(SquareContent.WHITE_MAN);
            game.getBoard().getWhitePieces().add(new Man(PieceColor.WHITE,
                    game.getBoard().getSquare(new SquareCoordinates(1, 0))));
            game.getBoard().getSquare(new SquareCoordinates(0, 1))
                    .setSquareContent(SquareContent.BLACK_MAN);
            game.getBoard().getBlackPieces().add(new Man(PieceColor.BLACK,
                    game.getBoard().getSquare(new SquareCoordinates(0, 1))));
            assertTrue(game.checkDrawCondition());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

}

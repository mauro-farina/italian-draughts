/*package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void checkPlayer1() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
            game.initGame();
            for(int i = 10; i >= 0; i--) {
                BoardUtils.removePiece(game.getBoard(), game.getBoard().getBlackPieces().get(i));
            }
            game.getBoard().getBlackPieces().get(0).setSquare(game.getBoard()
                    .getSquare(new SquareCoordinates(0, 3)));
            game.changeTurn();
            assertTrue(game.checkDrawCondition());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

}
*/

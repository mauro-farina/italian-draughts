package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void checkPlayer1() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE), new Player("Mauro", PlayerColor.BLACK));
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
            for(int i = 11; i >= 0; i--) {
                game.getBoard().removePiece(PieceColor.WHITE, i);
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
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(game.checkVictoryCondition());
    }


}

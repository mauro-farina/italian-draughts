package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void checkTurn() {
        Game game;
        try {
            game = new Game();
            game.changeTurn();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getTurn(), PlayerColor.BLACK);
    }

    @Test
    void checkTurnCounter() {
        Game game;
        try {
            game = new Game();
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
            game = new Game();
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
            game = new Game();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(game.checkVictoryCondition());
    }

}

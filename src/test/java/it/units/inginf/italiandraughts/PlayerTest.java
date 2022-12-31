package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTest {

    @Test
    void checkNickname() {
        Player player;
        try {
            player = new Player("Luca", PlayerColor.WHITE);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals("Luca", player.getNickname());
    }

    @Test
    void checkColor() {
        Player player;
        try {
            player = new Player("Luca", PlayerColor.WHITE);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(PlayerColor.WHITE, player.getColor());
    }

}

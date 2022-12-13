package it.units.inginf.italiandraughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test
    void checkSquareColor() {
        // Arrange
        int x=0, y=0; // coordinates for square A1
        // Act
        Square square;
        try {
            square = new Square(x, y);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        // Assert
        SquareColor squareColor = square.getSquareColor();
        assertEquals(squareColor, SquareColor.WHITE);
    }
}

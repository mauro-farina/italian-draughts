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

    @Test
    void checkSquareCoordinateX(){
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
        char coordinateX = square.getCoordinateX();
        assertEquals(coordinateX, 'A');
    }

    @Test
    void checkSquareCoordinateY(){
        // Arrange
        int x=2, y=5; // coordinates for square C6
        // Act
        Square square;
        try {
            square = new Square(x, y);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        // Assert
        char coordinateY = square.getCoordinateY();
        assertEquals(coordinateY, '6');
    }

}

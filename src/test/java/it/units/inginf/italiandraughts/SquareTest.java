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

    @Test
    void checkSquareContent(){
        int x=2, y=5;
        Square square;
        try {
            square = new Square(x, y);
            square.setSquareContent(SquareContent.BLACK_MAN);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(SquareContent.BLACK_MAN, square.getSquareContent());
    }

    @Test
    void checkSquareIsFree(){
        int x=2, y=5;
        Square square;
        try {
            square = new Square(x, y);
            square.setSquareContent(SquareContent.BLACK_MAN);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(square.isFree());
    }
    
    @Test
    void checkLastRow() {
        int x=2, y=5;
        Square square;
        try{
            square = new Square(x, y);
            square.updateSquare(SquareContent.BLACK_MAN);
        } catch(Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(square.getLastRow(), LastRow.BLACK);
    }
}

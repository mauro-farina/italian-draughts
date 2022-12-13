package it.units.inginf.italiandraughts;

public class Square {

    private final char[] coordinates = new char[2]; //coordinates of the cell ( from A1 to H8)

    public Square(int x, int y) throws Exception {
        switch (x) {
            case 0 -> coordinates[0] = 'A';
            case 1 -> coordinates[0] = 'B';
            case 2 -> coordinates[0] = 'C';
            case 3 -> coordinates[0] = 'D';
            case 4 -> coordinates[0] = 'E';
            case 5 -> coordinates[0] = 'F';
            case 6 -> coordinates[0] = 'G';
            case 7 -> coordinates[0] = 'H';
            default -> throw new Exception("Coordinates accepted value are integers from 0 to 7 included");
        }
        switch (y) {
            case 0 -> coordinates[1] = '1';
            case 1 -> coordinates[1] = '2';
            case 2 -> coordinates[1] = '3';
            case 3 -> coordinates[1] = '4';
            case 4 -> coordinates[1] = '5';
            case 5 -> coordinates[1] = '6';
            case 6 -> coordinates[1] = '7';
            case 7 -> coordinates[1] = '8';
            default -> throw new Exception("Coordinates accepted value are integers from 0 to 7 included");
        }
    }

    public char getCoordinateX() { // columns: A, B, ..., H
        return this.coordinates[0];
    }

    public char getCoordinateY() { // rows: 1, 2, ... 8
        return this.coordinates[1];
    }

}

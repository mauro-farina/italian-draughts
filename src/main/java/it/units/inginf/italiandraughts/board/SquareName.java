package it.units.inginf.italiandraughts.board;

public class SquareName {
    private final char row; // 1, 2, ..., 8
    private final char column; // A, B, ..., H

    public SquareName(char column, char row) throws Exception {
        if(column < 'A' || column > 'H') {
            throw new Exception("Column value must be in A to H range");
        }
        if(row < '1' || row > '8') {
            throw new Exception("Row value must be in 1 to 8 range");
        }
        this.row = row;
        this.column = column;
    }

    public SquareName(SquareCoordinates squareCoordinates) throws Exception {
        if(squareCoordinates == null) {
            throw new Exception("square coordinates cannot be null");
        }
        int x = squareCoordinates.getCoordinateX();
        int y = squareCoordinates.getCoordinateY();
        switch (x) {
            case 0 -> this.column = 'A';
            case 1 -> this.column = 'B';
            case 2 -> this.column = 'C';
            case 3 -> this.column = 'D';
            case 4 -> this.column = 'E';
            case 5 -> this.column = 'F';
            case 6 -> this.column = 'G';
            case 7 -> this.column = 'H';
            default -> throw new Exception("Coordinates accepted value are integers from 0 to 7 included");
        }
        switch (y) {
            case 0 -> this.row = '1';
            case 1 -> this.row = '2';
            case 2 -> this.row = '3';
            case 3 -> this.row = '4';
            case 4 -> this.row = '5';
            case 5 -> this.row = '6';
            case 6 -> this.row = '7';
            case 7 -> this.row = '8';
            default -> throw new Exception("Coordinates accepted value are integers from 0 to 7 included");
        }
    }

    public char getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return column+""+row;
    }
}

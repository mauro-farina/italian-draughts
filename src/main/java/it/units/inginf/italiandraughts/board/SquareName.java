package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareNameException;

public class SquareName {
    private final char row; // 1, 2, ..., 8
    private final char column; // A, B, ..., H

    public SquareName(char column, char row) throws CoordinatesException {
        if(column < 'A' || (column > 'H' && column < 'a') || column > 'h') {
            throw new CoordinatesException("SquareName.squareName() -> column value must be in A to H range");
        }
        if(row < '1' || row > '8') {
            throw new CoordinatesException("SquareName.squareName() -> row value must be in 1 to 8 range");
        }
        this.column = column > 'H' ? (char) (column - 32) : column;
        this.row = row;
    }

    public SquareName(String squareName) throws SquareNameException, CoordinatesException {
        if(squareName.trim().length() != 2) {
            throw new SquareNameException("SquareName.squareName() because does not accept this value" + 
                    squareName + " of SquareName");
        }
        this.column = squareName.toUpperCase().charAt(0);
        this.row = squareName.toUpperCase().charAt(1);
        if(column < 'A' || column > 'H') {
            throw new CoordinatesException("SquareName.squareName() -> column value must be in A to H range");
        }
        if(row < '1' || row > '8') {
            throw new CoordinatesException("SquareName.squareName() -> row value must be in 1 to 8 range");
        }
    }

    public SquareName(SquareCoordinates squareCoordinates) throws CoordinatesException {
        if(squareCoordinates == null) {
            throw new CoordinatesException("SquareName.squareName() -> square coordinates cannot be null");
        }
        int x = squareCoordinates.getColumn();
        int y = squareCoordinates.getRow();
        switch (x) {
            case 0 -> this.column = 'A';
            case 1 -> this.column = 'B';
            case 2 -> this.column = 'C';
            case 3 -> this.column = 'D';
            case 4 -> this.column = 'E';
            case 5 -> this.column = 'F';
            case 6 -> this.column = 'G';
            case 7 -> this.column = 'H';
            default -> throw new CoordinatesException("SquareName.squareName()-> coordinates accepted value are integers from 0 to 7 included");
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
            default -> throw new CoordinatesException("Coordinates accepted value are integers from 0 to 7 included");
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

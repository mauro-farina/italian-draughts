package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareNameException;

public class SquareCoordinates {

    private final int row; // coordinate Y
    private final int column; // coordinate X

    public SquareCoordinates(int column, int row) throws CoordinatesException {
        if(row < 0 || row >= 8) {
            throw new CoordinatesException("SquareCoordinates.SquareCoordinates() -> row value must be in 0 to 7 range");
        }
        if(column < 0 || column >= 8) {
            throw new CoordinatesException("SquareCoordinates.SquareCoordinates() -> column value must be in 0 to 7 range");
        }
        this.row = row;
        this.column = column;
    }

    public SquareCoordinates(SquareName squareName) throws CoordinatesException, SquareNameException {
        if(squareName == null) {
            throw new SquareNameException("SquareCoordinates.SquareCoordinates() because SquareName cannot be null");
        }
        int[] integerCoordinates = convertSquareNameToIntegerCoordinates(squareName);
        this.column = integerCoordinates[0];
        this.row = integerCoordinates[1];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    @Override
    public String toString() {
        return "("+column+","+row+")";
    }
    
    public boolean equals(SquareCoordinates otherSquareCoordinates) {
        return this.row == otherSquareCoordinates.getRow() && this.column == otherSquareCoordinates.getColumn();
    }

    private int[] convertSquareNameToIntegerCoordinates(SquareName squareName) throws CoordinatesException {
        int[] coordinates = new int[2];
        if(squareName.toString().length() != 2) {
            throw new CoordinatesException("Invalid square " + squareName);
        } else {
            switch(squareName.getColumn()) {
                case 'A' -> coordinates[0] = 0;
                case 'B' -> coordinates[0] = 1;
                case 'C' -> coordinates[0] = 2;
                case 'D' -> coordinates[0] = 3;
                case 'E' -> coordinates[0] = 4;
                case 'F' -> coordinates[0] = 5;
                case 'G' -> coordinates[0] = 6;
                case 'H' -> coordinates[0] = 7;
                default -> throw new CoordinatesException("Invalid square " + squareName);
            }
            switch(squareName.getRow()) {
                case '1' -> coordinates[1] = 0;
                case '2' -> coordinates[1] = 1;
                case '3' -> coordinates[1] = 2;
                case '4' -> coordinates[1] = 3;
                case '5' -> coordinates[1] = 4;
                case '6' -> coordinates[1] = 5;
                case '7' -> coordinates[1] = 6;
                case '8' -> coordinates[1] = 7;
                default -> throw new CoordinatesException("Invalid square " + squareName);
            }
            return coordinates;
        }
    }
    
}

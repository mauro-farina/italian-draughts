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
            if(squareName.getColumn() != 'A') {
                coordinates[0] = getColumnCoordinates(squareName.getColumn());
            }
            if(squareName.getRow() != '1') {
                coordinates[1] = getRowCoordinates(squareName.getRow());
            }
            return coordinates;
        }
    }

    private int getColumnCoordinates(char column) throws CoordinatesException{
        int columnCoordinate;
        switch(column) {
            case 'B' -> columnCoordinate = 1;
            case 'C' -> columnCoordinate = 2;
            case 'D' -> columnCoordinate = 3;
            case 'E' -> columnCoordinate = 4;
            case 'F' -> columnCoordinate = 5;
            case 'G' -> columnCoordinate = 6;
            case 'H' -> columnCoordinate = 7;
            default -> throw new CoordinatesException("SquareCoordinates.getColumnCoordinates() does not accept this coordinate " + column);
        }
        return columnCoordinate;
    }

    private int getRowCoordinates(char row) throws CoordinatesException {
        int rowCoordinate;
        switch(row) {
            case '2' -> rowCoordinate = 1;
            case '3' -> rowCoordinate = 2;
            case '4' -> rowCoordinate = 3;
            case '5' -> rowCoordinate = 4;
            case '6' -> rowCoordinate = 5;
            case '7' -> rowCoordinate = 6;
            case '8' -> rowCoordinate = 7;
            default -> throw new CoordinatesException("SquareCoordinates.getColumnCoordinates() does not accept this coordinate " + row);
        }
        return rowCoordinate;
    }
    
}

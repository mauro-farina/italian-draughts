package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.Utils;

public class SquareCoordinates {

    private final int row; // coordinate Y
    private final int column; // coordinate X

    public SquareCoordinates(int column, int row) throws Exception {
        if(row < 0 || row >= 8) {
            throw new Exception("Row value must be in 0 to 7 range");
        }
        if(column < 0 || column >= 8) {
            throw new Exception("Column value must be in 0 to 7 range");
        }
        this.row = row;
        this.column = column;
    }

    public SquareCoordinates(SquareName squareName) throws Exception {
        if(squareName == null) {
            throw new Exception("squareName cannot be null");
        }
        this.column = Utils.convertToCoordinates(squareName.toString())[0];
        this.row = Utils.convertToCoordinates(squareName.toString())[1];
    }

    public int getRow() {
        return row;
    }
    public int getCoordinateY() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getCoordinateX() {
        return column;
    }

    @Override
    public String toString() {
        return "("+column+","+row+")";
    }
}

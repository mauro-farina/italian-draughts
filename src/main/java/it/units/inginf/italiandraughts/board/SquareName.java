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

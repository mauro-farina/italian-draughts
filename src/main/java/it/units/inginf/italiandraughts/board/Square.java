package it.units.inginf.italiandraughts.board;

public class Square {

    private final char[] coordinates = new char[2]; //coordinates of the cell ( from A1 to H8)
    private final SquareColor squareColor; // square color (black or white)
    // italian-draughts pieces are only allowed on black squares
    private SquareContent squareContent;
    //private final PieceColor lastRowFor;
    // (y == 0, or coordinates[1] == 1) is the last row for Black Pieces
    // (y == 7, or coordinates[1] == 8) is the last row for White Pieces

    private final SquareCoordinates squareCoordinates;
    private final SquareName squareName;

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

        this.squareCoordinates = new SquareCoordinates(x,y);
        this.squareName = new SquareName(coordinates[0], coordinates[1]);

        // Assign color to square (purely based on square coordinates)
        if(x % 2 == 0){ // if x (column) even => B, D, F, H
            if(y % 2 == 0){ // if y (row) even => 2, 4, 6, 8
                this.squareColor = SquareColor.WHITE; //example: x=1, y=1 => square B2 => white
            } else { // if y (row) odd => 1, 3, 5, 7
                this.squareColor = SquareColor.BLACK; //example: x=1, y=0 => square B1 => black
            }
        } else { // if x (column) odd => A, C, E, G
            if(y % 2 == 0){ // if y (row) even
                this.squareColor = SquareColor.BLACK; //example: x=0, y=1 => square A2 => black
            } else { // if y (row) odd
                this.squareColor = SquareColor.WHITE; //example: x=0, y=0 => square A1 => white
            }
        }

        // LastRow
        /*if(y == 0) { // first row => a black man becomes a black king
            this.lastRowFor = PieceColor.BLACK;
        } else if (y == 7) { // last row => a white man becomes a white king
            this.lastRowFor = PieceColor.WHITE;
        } else { // if y is not a last row, no piece will become a king
            this.lastRowFor = null;
        }*/
        this.squareContent = SquareContent.EMPTY;
    }

    public SquareColor getSquareColor() {
        return this.squareColor;
    }

    public SquareContent getSquareContent() {
        return squareContent;
    }
    
    /*
    public boolean isLastRowFor(PieceColor pieceColor) {
        return pieceColor.equals(this.lastRowFor);
    }*/

    public SquareCoordinates getSquareCoordinates() {
        return squareCoordinates;
    }

    public SquareName getSquareName() {
        return squareName;
    }

    public void setSquareContent(SquareContent newSquareContent) throws Exception {
        if (newSquareContent == null){
            throw new Exception("Square content cannot be null");
        } else {
            this.squareContent = newSquareContent;
        }
    }

    public boolean isFree() {
        return squareContent.equals(SquareContent.EMPTY);
    }
}

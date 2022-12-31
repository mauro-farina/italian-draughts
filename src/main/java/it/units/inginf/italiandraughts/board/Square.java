package it.units.inginf.italiandraughts.board;

public class Square {

    private final SquareColor squareColor; // square color (black or white)
    // italian-draughts pieces are only allowed on black squares
    private SquareContent squareContent;
    private final SquareCoordinates squareCoordinates;
    private final SquareName squareName;

    public Square(int x, int y) throws Exception {
        this.squareCoordinates = new SquareCoordinates(x,y);
        this.squareName = new SquareName(squareCoordinates);

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
        this.squareContent = SquareContent.EMPTY;
    }

    public SquareColor getSquareColor() {
        return this.squareColor;
    }

    public SquareCoordinates getSquareCoordinates() {
        return squareCoordinates;
    }

    public SquareName getSquareName() {
        return squareName;
    }

    public SquareContent getSquareContent() {
        return squareContent;
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

package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;

public class Man implements Piece{
    protected PieceColor pieceColor; // color of the piece, either black or white
    protected Square square; // current square on which the piece is positioned

    public Man(PieceColor pieceColor, Square square) throws PieceColorException, SquareException {
        if((pieceColor != PieceColor.WHITE) && (pieceColor != PieceColor.BLACK)) {
            throw new PieceColorException((isMan() ? "Man" : "King") + "() does not accept this PieceColor");
        }
        checkSquareIsValid(square);
        this.pieceColor = pieceColor;
        this.square = square;
    }

    @Override
    public PieceColor getColor() {
        return this.pieceColor;
    }

    @Override
    public Square getSquare() {
        return this.square;
    }

    @Override
    public void setSquare(Square newSquare) throws SquareException {
        checkSquareIsValid(square);
        this.square = newSquare;
    }

    private void checkSquareIsValid(Square square) throws SquareException {
        if(square == null)
            throw new SquareException((isMan() ? "Man" : "King") + ".setSquare() does not accept this Square because it is null");
        if(!square.getSquareColor().equals(SquareColor.BLACK)) {
            throw new SquareException((isMan() ? "Man" : "King") + ".setSquare() does not accept this Square," +
                    " because pieces cannot be on white squares");
        }
    }

    @Override
    public boolean isMan() {
        return true;
    }

    @Override
    public boolean isKing() {
        return false;
    }

}

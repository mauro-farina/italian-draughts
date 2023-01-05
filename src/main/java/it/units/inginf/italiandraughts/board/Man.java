package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;

public class Man implements Piece{
    protected PieceColor pieceColor; // color of the piece, either black or white
    protected Square square; // current square on which the piece is positioned

    public Man(PieceColor pieceColor, Square square) throws PieceColorException, SquareException {
        if((pieceColor != PieceColor.WHITE) && (pieceColor != PieceColor.BLACK)) {
            throw new PieceColorException("Man.Man() does not accept this PieceColor");
        } else {
            this.pieceColor = pieceColor;
        }
        if(!square.getSquareColor().equals(SquareColor.BLACK)) {
            throw new SquareException("Man.Man() does not accept this Square," +
                    " because pieces cannot be on white squares");
        } else {
            this.square = square;
        }
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
        if(!newSquare.getSquareColor().equals(SquareColor.BLACK)) {
            throw new SquareException("Man.setSquare() does not accept this Square," +
                    " because pieces cannot be on white squares");
        } else {
            this.square = newSquare;
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

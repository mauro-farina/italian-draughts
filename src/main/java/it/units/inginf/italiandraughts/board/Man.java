package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.PieceException;

public class Man implements Piece{
    protected PieceColor pieceColor; // color of the piece, either black or white
    protected Square square; // current square on which the piece is positioned

    public Man(PieceColor pieceColor, Square square) throws PieceColorException, SquareException {
        if((pieceColor != PieceColor.WHITE) && (pieceColor != PieceColor.BLACK)) {
            if(isMan()) {
                throw new PieceColorException("Man.Man() does not accept this PieceColor");
            } else if (isKing()) {
                throw new PieceColorException("King.King() does not accept this PieceColor");
            }
        } else {
            this.pieceColor = pieceColor;
        }
        if(square == null) {
            if(isMan()) {
                throw new SquareException("Man.man() does not accept this Square because it is null");
            } else if(isKing()) {
                throw new SquareException("King.king() does not accept this Square because it is null");
            }
        } else if(!square.getSquareColor().equals(SquareColor.BLACK)) {
            if(isMan()) {
                throw new SquareException("Man.Man() does not accept this Square," +
                        " because pieces cannot be on white squares");
            } else if(isKing()) {
                throw new SquareException("King.King() does not accept this Square," +
                        " because pieces cannot be on white squares");
            }
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
    public void setSquare(Square newSquare) throws SquareException, PieceException {
        if(square == null) {
            if(isMan()) {
                throw new SquareException("Man.setSquare() does not accept this Square because it is null");
            } else {
                throw new SquareException("King.setSquare() does not accept this Square because it is null");
            }
        } else if(!square.getSquareColor().equals(SquareColor.BLACK)) {
            if(isMan()) {
                throw new SquareException("Man.setSquare() does not accept this Square," +
                        " because pieces cannot be on white squares");
            } else {
                throw new SquareException("King.setSquare()) does not accept this Square," +
                        " because pieces cannot be on white squares");
            }
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

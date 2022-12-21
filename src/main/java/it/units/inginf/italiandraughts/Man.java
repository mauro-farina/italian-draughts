package it.units.inginf.italiandraughts;
import java.lang.Exception;

public class Man implements Piece{
    protected PieceColor color; // color of the piece, either black or white
    protected Square square; // current square on which the piece is positioned
    //protected ManSymbol symbol;

    public Man(PieceColor color, Square square) throws Exception {
        if(color == null){
            throw new Exception("Color cannot be null");
        } else {
            this.color = color;
        }
        if(square.getSquareColor().equals(SquareColor.WHITE)) {
            throw new Exception("Pieces cannot be on white squares");
        } else {
            this.square = square;
        }
    }
    
    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public Square getSquare() {
        return this.square;
    }

    public void setSquare(Square newSquare) throws Exception {
        if(newSquare.getSquareColor().equals(SquareColor.WHITE)) {
            throw new Exception("Pieces cannot be on white squares");
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

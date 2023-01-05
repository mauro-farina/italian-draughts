package it.units.inginf.italiandraughts.board;
import java.lang.Exception;

public class Man implements Piece{
    
    protected PieceColor pieceColor; // color of the piece, either black or white
    protected Square square; // current square on which the piece is positioned

    public Man(PieceColor pieceColor, Square square) throws Exception {
        if(pieceColor == null){
            throw new Exception("Color cannot be null");
        } else {
            this.pieceColor = pieceColor;
        }
        if(!square.getSquareColor().equals(SquareColor.BLACK)) {
            throw new Exception("Pieces cannot be on white squares");
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
    public void setSquare(Square newSquare) throws Exception {
        if(!newSquare.getSquareColor().equals(SquareColor.BLACK)) {
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

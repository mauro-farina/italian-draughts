package it.units.inginf.italiandraughts.board;
import it.units.inginf.italiandraughts.exception.SquareException;

public interface Piece {

    PieceColor getColor();
    Square getSquare();
    boolean isMan();
    boolean isKing();
    void setSquare(Square square) throws SquareException;
}

package it.units.inginf.italiandraughts;

public interface Piece {

    PieceColor getColor();
    Square getSquare();
    boolean isMan();
    boolean isKing();
    void setSquare(Square square) throws Exception;
}

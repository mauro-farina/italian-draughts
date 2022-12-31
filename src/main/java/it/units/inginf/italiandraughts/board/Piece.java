package it.units.inginf.italiandraughts.board;

public interface Piece {

    PieceColor getColor();
    Square getSquare();
    boolean isMan();
    boolean isKing();
    void setSquare(Square square) throws Exception;
}

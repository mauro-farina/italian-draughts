package it.units.inginf.italiandraughts;

public interface OutputPrinter {
    void printBoard(Board board);
    void printBoardForPlayer(Board board, PlayerColor playerColor);
}

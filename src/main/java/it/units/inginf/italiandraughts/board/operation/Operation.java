package it.units.inginf.italiandraughts.board.operation;

import it.units.inginf.italiandraughts.board.Board;

public class Operation {

    protected Board board;
    protected OperationType operationType;
    public Operation(Board board, OperationType operationType) {
        this.board = board;
        this.operationType = operationType;
    }

    public Board getBoard() {
        return this.board;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }
}

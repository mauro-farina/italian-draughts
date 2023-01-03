package it.units.inginf.italiandraughts.board.operation;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.Piece;

public class OperationResearchPiece extends Operation {

    Square square;

    public OperationResearchPiece(Board board, Square square) {
        super(board, OperationType.RESEARCH_PIECE);
        this.square = square;
    }

    public Square getSquare() {
        return this.square;
    }

    public Piece getPieceSought() {
        for(int i = 0; i < board.getWhitePieces().size(); i++) {
            if(board.getWhitePieces().get(i).getSquare() == square) {
                return board.getWhitePieces().get(i);
            }
        }
        for(int i = 0; i < board.getBlackPieces().size(); i++) {
            if(board.getBlackPieces().get(i).getSquare() == square) {
                return board.getBlackPieces().get(i);
            }
        }
        return null;
    }
}

package it.units.inginf.italiandraughts.board.operation;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.PieceColor;

public class OperationRemovePiece extends Operation {

    private PieceColor pieceColor;
    private int index;

    public OperationRemovePiece(Board board, PieceColor pieceColor, int index) throws Exception {
        super(board, OperationType.REMOVE_PIECE);
        this.pieceColor = pieceColor;
        this.index = index;
    }

    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    public int getIndex() {
        return this.index;
    }

    public void run() throws Exception {
        if(pieceColor == PieceColor.WHITE) {
            if(this.board.getWhitePieces().get(index) == null) {
                throw new Exception("This piece does not exist");
            } else {
                this.board.getWhitePieces().remove(index);
            }
        } else if(pieceColor == PieceColor.BLACK) {
            if(this.board.getBlackPieces().get(index) == null) {
                throw new Exception("This piece does not exist");
            } else {
                this.board.getBlackPieces().remove(index);
            }
        } else {
            throw new Exception("This color is invalid");
        }
    }
}

package it.units.inginf.italiandraughts.board.operation;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.SquareContent;

public class OperationManBecomesKing extends Operation {

    private PieceColor pieceColor;
    private int index;

    public OperationManBecomesKing(Board board, PieceColor pieceColor, int index) {
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

    public void run() throws Exception{
        if(pieceColor == PieceColor.WHITE) {
            if(this.board.getWhitePieces().get(index) == null) {
                throw new Exception("This piece does not exist");
            } else {
                this.board.getWhitePieces().add(new King(pieceColor, this.board.getWhitePieces().get(index).getSquare()));
                this.board.getWhitePieces().get(index).getSquare().setSquareContent(SquareContent.WHITE_KING);
                new OperationRemovePiece(this.board, pieceColor, index).run();
            }
        } else if(pieceColor == PieceColor.BLACK) {
            if(this.board.getBlackPieces().get(index) == null) {
                throw new Exception("This piece does not exist");
            } else {
                this.board.getBlackPieces().add(new King(pieceColor, this.board.getBlackPieces().get(index).getSquare()));
                this.board.getBlackPieces().get(index).getSquare().setSquareContent(SquareContent.BLACK_KING);
                new OperationRemovePiece(this.board, pieceColor, index).run();
            }
        } else {
            throw new Exception("This color is invalid");
        }
    }
}

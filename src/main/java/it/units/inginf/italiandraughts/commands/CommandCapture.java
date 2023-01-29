package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareName;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.CommandException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareException;

import java.util.List;

public class CommandCapture extends Command {

    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates pieceToCaptureCoordinates;
    private final SquareCoordinates toCoordinates;

    public CommandCapture(SquareCoordinates fromCoordinates, SquareCoordinates pieceToCaptureCoordinates) throws CoordinatesException {
        super(CommandType.CAPTURE);
        this.fromCoordinates = fromCoordinates;
        this.pieceToCaptureCoordinates = pieceToCaptureCoordinates;
        int toCoordinateY, toCoordinateX;
        if(fromCoordinates.getRow() > pieceToCaptureCoordinates.getRow()){ // *6 capture *5 (ex: C6 capture B5)
            toCoordinateY = pieceToCaptureCoordinates.getRow() - 1; // moves to *4
        } else { // *3 capture *4 (ex: B3 capture C4)
            toCoordinateY = pieceToCaptureCoordinates.getRow() + 1; // moves to *5
        }
        if(fromCoordinates.getColumn() > pieceToCaptureCoordinates.getColumn()) { // ex: D* capture C* (ex: D3 capture C4)
            toCoordinateX = pieceToCaptureCoordinates.getColumn() - 1; // moves to B*
        } else { // B* capture C* (ex: B3 capture C4)
            toCoordinateX = pieceToCaptureCoordinates.getColumn() + 1; // moves to D*
        }
        try {
            this.toCoordinates = new SquareCoordinates(toCoordinateX, toCoordinateY);
        } catch (Exception exception) {
            throw new CoordinatesException("CommandCapture.CommandCapture() -> cannot capture piece on the edge of the board");
        }
    }

    public boolean isValid(Board board) throws SquareException, BoardException, CommandException {
        if(board == null) {
            throw new BoardException("CommandCapture.isValid() does not accept this command, because board is null");
        }
        if(this.fromCoordinates == null || this.pieceToCaptureCoordinates == null || this.toCoordinates == null) {
            return false;
        }

        Square selectedPieceSquare;
        Square capturedPieceSquare;
        Square arrivalSquare;
        try {
            selectedPieceSquare = board.getSquare(this.fromCoordinates);
            capturedPieceSquare = board.getSquare(this.pieceToCaptureCoordinates);
            arrivalSquare = board.getSquare(toCoordinates);
        } catch (CoordinatesException e) {
            return false;
        }
        Piece selectedPiece = BoardUtils.researchPiece(board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(board, capturedPieceSquare);

        if(selectedPiece == null) {
            throw new CommandException("CommandCapture.isValid(): there are no pieces on " + selectedPieceSquare.getSquareName());
        }
        if(capturedPiece == null) {
            throw new CommandException("CommandCapture.isValid() : there are no pieces on " + selectedPieceSquare.getSquareName());
        }
        if(selectedPiece.getColor() == capturedPiece.getColor()) {
            throw new CommandException("CommandCapture.isValid() does not accept this PieceColor because you cannot capture your own pieces");
        }
        if(selectedPiece.isMan() && capturedPiece.isKing()) {
            throw new CommandException("CommandCapture.isValid() does not accept this pieces because a man cannot capture a king");
        }
        if(!arrivalSquare.isFree()) {
            throw new CommandException("CommandCapture.isValid(): cannot capture piece on " + capturedPieceSquare.getSquareName()
                    + ": there is a piece on " + arrivalSquare.getSquareName());
        }
        List<Square> reachableSquaresFromSelectedPiece = board.getReachableSquares(selectedPiece);
        for(Square reachableSquare : reachableSquaresFromSelectedPiece) {
            if (capturedPieceSquare == reachableSquare) {
                return true;
            }
        }
        return false;
    }

    public SquareCoordinates getFromCoordinates() {
        return this.fromCoordinates;
    }

    public SquareCoordinates getPieceToCaptureCoordinates() {
        return this.pieceToCaptureCoordinates;
    }

    public SquareCoordinates getToCoordinates() {
        return this.toCoordinates;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof CommandCapture)) { return false; }
        return this.fromCoordinates.equals(((CommandCapture)other).getFromCoordinates())
                && this.pieceToCaptureCoordinates.equals(((CommandCapture)other).getPieceToCaptureCoordinates());
    }

    @Override
    public String toString() {
        try {
            return new SquareName(this.getFromCoordinates()) + " CAPTURE " + new SquareName(this.getPieceToCaptureCoordinates());
        } catch (CoordinatesException e) {
            return "Invalid Command";
        }
    }
}

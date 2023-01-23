package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.*;
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
            throw new BoardException("Invalid command: board is null");
        }
        if(this.fromCoordinates == null || this.pieceToCaptureCoordinates == null || this.toCoordinates == null) {
            return false;
        }
        Square selectedPieceSquare = board.getSquare(this.fromCoordinates);
        Square capturedPieceSquare = board.getSquare(this.pieceToCaptureCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(board, capturedPieceSquare);

        if(selectedPiece == null) {
            throw new CommandException("There are no pieces on " + selectedPieceSquare.getSquareName());
        }
        if(capturedPiece == null) {
            throw new CommandException("There are no pieces on " + selectedPieceSquare.getSquareName());
        }
        if(selectedPiece.getColor() == capturedPiece.getColor()) {
            throw new CommandException("You cannot capture your own pieces");
        }
        if(selectedPiece.isMan() && capturedPiece.isKing()) {
            throw new CommandException("A man cannot capture a king");
        }
        if(!board.getSquare(toCoordinates).isFree()) {
            throw new CommandException("Cannot capture piece on " + capturedPieceSquare.getSquareName()
                    + ": there is a piece on " + board.getSquare(this.toCoordinates).getSquareName());
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
            throw new RuntimeException(e);
        }
    }
}
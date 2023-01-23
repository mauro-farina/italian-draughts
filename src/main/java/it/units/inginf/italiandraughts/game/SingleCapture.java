package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.*;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;

public class SingleCapture extends CommandCapture {

    private final Board board;
    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates pieceToCaptureCoordinates;
    private final SquareCoordinates toCoordinates;
    private boolean capturedPieceIsKing;

    public SingleCapture(Board board, SquareCoordinates fromCoordinates,
                         SquareCoordinates pieceToCaptureCoordinates) throws CoordinatesException {
        super(fromCoordinates, pieceToCaptureCoordinates);
        this.board = board;
        this.fromCoordinates = getFromCoordinates();
        this.toCoordinates = getToCoordinates();
        this.pieceToCaptureCoordinates = getPieceToCaptureCoordinates();
    }

    public boolean isValid() throws SquareException, BoardException {
        if(this.fromCoordinates == null || this.pieceToCaptureCoordinates == null || this.toCoordinates == null) {
            return false;
        }
        Piece selectedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.fromCoordinates));
        Piece capturedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.pieceToCaptureCoordinates));
        if(selectedPiece == null || capturedPiece == null) {
            return false;
        }
        if(selectedPiece.getColor() == capturedPiece.getColor()) {
            return false;
        }
        if(selectedPiece.isMan() && capturedPiece.isKing()) {
            return false;
        }
        if(!this.board.getSquare(toCoordinates).isFree()) {
            return false;
        }
        if((this.fromCoordinates.getRow() != this.pieceToCaptureCoordinates.getRow() + 1
                && this.fromCoordinates.getRow() != this.pieceToCaptureCoordinates.getRow() - 1)
                || (this.fromCoordinates.getColumn() != this.pieceToCaptureCoordinates.getColumn() + 1
                && this.fromCoordinates.getColumn() != this.pieceToCaptureCoordinates.getColumn() - 1)) {
            return false;
        }
        for(Square square: this.board.getReachableSquares(selectedPiece)) {
            if(square.getSquareCoordinates() == this.pieceToCaptureCoordinates) {
                return true;
            }
        }
        return false;
    }

    public void run() throws BoardException, SquareException, SquareContentException,
            PieceColorException, PieceException {
        Square selectedPieceSquare = board.getSquare(this.fromCoordinates);
        Square capturedPieceSquare = board.getSquare(this.pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(this.toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(this.board, capturedPieceSquare);
        if(selectedPiece != null && capturedPiece != null) {
            this.capturedPieceIsKing = capturedPiece.isKing();
            selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
            capturedPieceSquare.setSquareContent(SquareContent.EMPTY);
            BoardUtils.removePiece(this.board, capturedPiece);
            updateSquareContentToMatchPiece(destinationSquare, selectedPiece);
            selectedPiece.setSquare(destinationSquare);
        }
    }

    public void reset() throws BoardException, SquareException, SquareContentException,
            PieceColorException, PieceException {
        Square selectedPieceSquare = board.getSquare(fromCoordinates);
        Square capturedPieceSquare = board.getSquare(pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, destinationSquare);
        if(selectedPiece == null) {
            return;
        }
        destinationSquare.setSquareContent(SquareContent.EMPTY);
        updateSquareContentToMatchPiece(selectedPieceSquare, selectedPiece);
        selectedPiece.setSquare(selectedPieceSquare);
        if(capturedPieceIsKing) {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_KING);
                this.board.getBlackPieces().add(new King(PieceColor.BLACK, capturedPieceSquare));
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_KING);
                this.board.getWhitePieces().add(new King(PieceColor.WHITE, capturedPieceSquare));
            }
        } else {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_MAN);
                this.board.getBlackPieces().add(new Man(PieceColor.BLACK, capturedPieceSquare));
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_MAN);
                this.board.getWhitePieces().add(new Man(PieceColor.WHITE, capturedPieceSquare));
            }
        }
    }

    public boolean isCapturingPieceKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.fromCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    public boolean pieceOnToCoordinatesIsKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.toCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    public boolean isCapturedPieceKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.pieceToCaptureCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    private static void updateSquareContentToMatchPiece(Square square, Piece piece) throws SquareContentException {
        if (piece.isMan()) {
            if (piece.getColor() == PieceColor.WHITE) {
                square.setSquareContent(SquareContent.WHITE_MAN);
            } else {
                square.setSquareContent(SquareContent.BLACK_MAN);
            }
        } else { // isKing
            if (piece.getColor() == PieceColor.WHITE) {
                square.setSquareContent(SquareContent.WHITE_KING);
            } else {
                square.setSquareContent(SquareContent.BLACK_KING);
            }
        }
    }

}

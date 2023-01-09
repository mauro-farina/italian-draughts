package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.exception.*;

public class SingleCapture {

    private final Board board;
    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates pieceToCaptureCoordinates;
    private final SquareCoordinates toCoordinates;

    public SingleCapture(Board board, SquareCoordinates fromCoordinates,
                         SquareCoordinates pieceToCaptureCoordinates) throws CoordinatesException {
        this.board = board;
        this.fromCoordinates = fromCoordinates;
        this.pieceToCaptureCoordinates = pieceToCaptureCoordinates;
        int toCoordinateY, toCoordinateX;
        if(fromCoordinates.getCoordinateY() > pieceToCaptureCoordinates.getCoordinateY()){
            toCoordinateY = pieceToCaptureCoordinates.getCoordinateY() - 1;
        } else {
            toCoordinateY = pieceToCaptureCoordinates.getCoordinateY() + 1;
        }
        if(fromCoordinates.getCoordinateX() > pieceToCaptureCoordinates.getCoordinateX()) {
            toCoordinateX = pieceToCaptureCoordinates.getCoordinateX() - 1;
        } else {
            toCoordinateX = pieceToCaptureCoordinates.getCoordinateX() + 1;
        }
        if(toCoordinateY > - 1 && toCoordinateY < 8 && toCoordinateX > - 1 && toCoordinateX < 8) {
            this.toCoordinates = new SquareCoordinates(toCoordinateX, toCoordinateY);
        } else {
            this.toCoordinates = null;
        }
    }

    public SquareCoordinates getFromCoordinates() {
        return fromCoordinates;
    }

    public SquareCoordinates getPieceToCaptureCoordinates() {
        return pieceToCaptureCoordinates;
    }

    public SquareCoordinates getToCoordinates() {
        return toCoordinates;
    }

    public boolean isValid() throws SquareException, BoardException {
        if(fromCoordinates == null || pieceToCaptureCoordinates == null || toCoordinates == null) {
            return false;
        }
        Piece selectedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(fromCoordinates));
        Piece capturedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(pieceToCaptureCoordinates));
        if(selectedPiece == null || capturedPiece == null) {
            return false;
        }
        if(selectedPiece.getColor() == capturedPiece.getColor()) {
            return false;
        }
        if((this.fromCoordinates.getRow() != this.pieceToCaptureCoordinates.getRow() + 1
                && this.fromCoordinates.getRow() != this.pieceToCaptureCoordinates.getRow() - 1)
                || (this.fromCoordinates.getColumn() != this.pieceToCaptureCoordinates.getColumn() + 1
                && this.fromCoordinates.getColumn() != this.pieceToCaptureCoordinates.getColumn() - 1)) {
            return false;
        }
        return board.getSquare(toCoordinates).isFree();
    }

    public void run() throws BoardException, SquareException, SquareContentException,
            PieceColorException, PieceException {
        Square selectedPieceSquare = board.getSquare(fromCoordinates);
        Square capturedPieceSquare = board.getSquare(pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(this.board, capturedPieceSquare);
        if(selectedPiece != null && capturedPiece != null) {
            selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
            capturedPieceSquare.setSquareContent(SquareContent.EMPTY);
            BoardUtils.removePiece(board, capturedPiece);
            if (selectedPiece.isMan()) {
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    destinationSquare.setSquareContent(SquareContent.WHITE_MAN);
                } else {
                    destinationSquare.setSquareContent(SquareContent.BLACK_MAN);
                }
            } else { // isKing
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    destinationSquare.setSquareContent(SquareContent.WHITE_KING);
                } else {
                    destinationSquare.setSquareContent(SquareContent.BLACK_KING);
                }
            }
            selectedPiece.setSquare(destinationSquare);
        }
    }

    public void runBack() throws BoardException, SquareException, SquareContentException,
            PieceColorException {
        Square selectedPieceSquare = board.getSquare(fromCoordinates);
        Square capturedPieceSquare = board.getSquare(pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, destinationSquare);
        if(selectedPiece != null) {
            destinationSquare.setSquareContent(SquareContent.EMPTY);
            if (selectedPiece.isMan()) {
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    selectedPieceSquare.setSquareContent(SquareContent.WHITE_MAN);
                } else {
                    selectedPieceSquare.setSquareContent(SquareContent.BLACK_MAN);
                }
            } else { // isKing
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    selectedPieceSquare.setSquareContent(SquareContent.WHITE_KING);
                } else {
                    selectedPieceSquare.setSquareContent(SquareContent.BLACK_KING);
                }
            }
            selectedPiece.setSquare(selectedPieceSquare);
            if(pieceOnCaptureCoordinatesIsKing()) {
                if(selectedPiece.getColor() == PieceColor.WHITE) {
                    capturedPieceSquare.setSquareContent(SquareContent.BLACK_KING);
                    board.getBlackPieces().add(new King(PieceColor.BLACK, capturedPieceSquare));
                } else {
                    capturedPieceSquare.setSquareContent(SquareContent.WHITE_KING);
                    board.getWhitePieces().add(new King(PieceColor.WHITE, capturedPieceSquare));
                }
            } else {
                if(selectedPiece.getColor() == PieceColor.WHITE) {
                    capturedPieceSquare.setSquareContent(SquareContent.BLACK_MAN);
                    board.getBlackPieces().add(new Man(PieceColor.BLACK, capturedPieceSquare));
                } else {
                    capturedPieceSquare.setSquareContent(SquareContent.WHITE_MAN);
                    board.getWhitePieces().add(new Man(PieceColor.WHITE, capturedPieceSquare));
                }
            }
        }
    }

    public boolean pieceOnFromCoordinatesIsKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(fromCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    public boolean pieceOnToCoordinatesIsKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(toCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    private boolean pieceOnCaptureCoordinatesIsKing() throws BoardException, SquareException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(toCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

}

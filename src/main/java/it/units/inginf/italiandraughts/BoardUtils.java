package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.SquareColor;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils {

    public static void removePiece(Board board, Piece piece) throws BoardException, PieceException, PieceColorException {
        if(board == null) {
            throw new BoardException("BoardUtils.removePiece() does not accept this Board, because it is null");
        }
        if(piece == null) {
            throw new PieceException("BoardUtils.removePiece() does not accept this Piece, because it is null");
        }
        if(piece.getColor() != PieceColor.BLACK && piece.getColor() != PieceColor.WHITE) {
            throw new PieceColorException("BoardUtils.removePiece() does not accept this piece, because PieceColor is not valid");
        }
        List<Piece> piecesList = board.getPieces(piece.getColor());
        for(int i = 0; i < piecesList.size(); i++) {
            if (piecesList.get(i) == piece) {
                piecesList.remove(i);
                break;
            } else if(i == piecesList.size() - 1) {
                throw new PieceException("BoardUtils.removePiece() does not accept this Piece," +
                        " because it does not belong to the list of " + piece.getColor() + " pieces");
            }
        }
    }

    public static void toCrown(Board board, Piece piece) throws BoardException, PieceException, PieceColorException, SquareException, SquareContentException {
        if(board == null) {
            throw new BoardException("BoardUtils.toCrown() does not accept this Board, because it is null");
        }
        if(piece == null) {
            throw new PieceException("BoardUtils.toCrown() does not accept this Piece, because it is null");
        } else if (!piece.isMan()) {
            throw new PieceException("BoardUtils.toCrown() does not accept this Piece, because it is not a Man");
        }
        if(piece.getColor() != PieceColor.BLACK && piece.getColor() != PieceColor.WHITE) {
            throw new PieceColorException("BoardUtils.removePiece() does not accept this piece, because PieceColor is not valid");
        }
        List<Piece> piecesList = board.getPieces(piece.getColor());
        for(int i = 0; i < piecesList.size(); i++) {
            if (piecesList.get(i) == piece) {
                piecesList.add(new King(piece.getColor(), piecesList.get(i).getSquare()));
                piecesList.get(i).getSquare().setSquareContent(
                        piece.getColor() == PieceColor.WHITE ? SquareContent.WHITE_KING : SquareContent.BLACK_KING
                );
                piecesList.remove(i);
                break;
            } else if(i == piecesList.size() - 1) {
                throw new PieceException("BoardUtils.toCrown() does not accept this Piece," +
                        " because it does not belong on the WhitePieces list");
            }
        }
    }

    public static Piece researchPiece(Board board, Square square) throws BoardException, SquareException {
        if(board == null) {
            throw new BoardException("BoardUtils.researchPiece() does not accept this Board, because it is null");
        }
        if(square == null) {
            throw new SquareException("BoardUtils.researchPiece() does not accept this Square, because it is null");
        }
        if ((square.getSquareColor() == SquareColor.BLACK) || (!square.isFree())) {
            List<Piece> pieces = new ArrayList<>();
            pieces.addAll(board.getWhitePieces());
            pieces.addAll(board.getBlackPieces());
            return pieces.stream().filter(piece -> piece.getSquare().equals(square)).findAny().orElse(null);
        }
        return null;
    }

}

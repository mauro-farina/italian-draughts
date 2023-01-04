package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.*;

public class BoardUtils {

    public static void removePiece(Board board, Piece piece) throws Exception {
        if(piece == null) {
            throw new Exception("Invalid Piece");
        }
        if(piece.getColor() == PieceColor.WHITE) {
            for(int i = 0; i < board.getWhitePieces().size(); i++) {
                if (board.getWhitePieces().get(i) == piece) {
                    board.getWhitePieces().remove(i);
                    break;
                } else if(i == board.getWhitePieces().size() - 1) {
                    throw new Exception("Invalid Piece");
                }
            }
        } else if(piece.getColor() == PieceColor.BLACK) {
            for(int i = 0; i < board.getBlackPieces().size(); i++) {
                if (board.getBlackPieces().get(i) == piece) {
                    board.getBlackPieces().remove(i);
                    break;
                } else if(i == board.getWhitePieces().size() - 1) {
                    throw new Exception("Invalid Piece");
                }
            }
        } else {
            throw new Exception("Invalid PieceColor");
        }
    }

    public static void toCrown(Board board, Piece piece) throws Exception {
        if(board == null) {
            throw new Exception("Invalid Board");
        }
        if(piece == null) {
            throw new Exception("Piece cannot be null");
        } else if (!piece.isMan()) {
            throw new Exception("This piece is not a Man");
        }
        if(piece.getColor() == PieceColor.WHITE) {
            for(int i = 0; i < board.getWhitePieces().size(); i++) {
                if (board.getWhitePieces().get(i) == piece) {
                    board.getWhitePieces().add(new King(piece.getColor(), board.getWhitePieces().get(i).getSquare()));
                    board.getWhitePieces().get(i).getSquare().setSquareContent(SquareContent.WHITE_KING);
                    board.getWhitePieces().remove(i);
                    break;
                } else if(i == board.getWhitePieces().size() - 1) {
                    throw new Exception("Invalid Piece");
                }
            }
        } else if(piece.getColor() == PieceColor.BLACK) {
            for(int i = 0; i < board.getBlackPieces().size(); i++) {
                if (board.getBlackPieces().get(i) == piece) {
                    board.getBlackPieces().add(new King(piece.getColor(), board.getWhitePieces().get(i).getSquare()));
                    board.getBlackPieces().get(i).getSquare().setSquareContent(SquareContent.WHITE_KING);
                    board.getBlackPieces().remove(i);
                    break;
                } else if(i == board.getBlackPieces().size() - 1) {
                    throw new Exception("Invalid Piece");
                }
            }
        } else {
            throw new Exception("This color is invalid");
        }
    }
    
    public static Piece researchPiece(Board board, Square square) throws Exception {
        if(board == null) {
            throw new Exception("Board cannot be null");
        }
        if(square == null) {
            throw new Exception("Square cannot be null");
        }
        if ((square.getSquareColor() == SquareColor.BLACK) || (!square.isFree())) {
            for(int i = 0; i < board.getWhitePieces().size(); i++) {
                if(board.getWhitePieces().get(i).getSquare() == square) {
                    return board.getWhitePieces().get(i);
                }
            }
            for(int i = 0; i < board.getBlackPieces().size(); i++) {
                if (board.getBlackPieces().get(i).getSquare() == square) {
                    return board.getBlackPieces().get(i);
                }
            }
        }
        return null;
    }

}

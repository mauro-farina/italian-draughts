package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareNameException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.PlayerColorException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Square[][] boardSquares;

    private List<Piece> whitePieces;
    private List<Piece> blackPieces;

    public Board() throws SquareNameException, SquareContentException, CoordinatesException, PieceColorException, SquareException {
        boardSquares = new Square[8][8];
        for(byte i=0; i<8; i++){
            for(byte j=0; j<8; j++) {
                boardSquares[i][j] = new Square(i, j);
            }
        }
        initBoard();
    }

    private void initBoard() throws SquareContentException, CoordinatesException, PieceColorException, SquareException {
        whitePieces = new ArrayList<>();
        // add White initial pieces (rows 1, 2, 3)
        for(byte i=0; i<3; i++){
            for(byte j=0; j<8; j+=2){
                if(i % 2 == 0 && j == 0){
                    j--;
                    continue;
                }
                SquareCoordinates squareCoordinates = new SquareCoordinates(j, i);
                getSquare(squareCoordinates).setSquareContent(SquareContent.WHITE_MAN);
                whitePieces.add(new Man(PieceColor.WHITE, getSquare(squareCoordinates)));
            }
        }
        blackPieces = new ArrayList<>();
        // add Black initial pieces (rows 6, 7, 8)
        for(byte i=5; i<8; i++){
            for(byte j=0; j<8; j+=2){
                if(i % 2 == 0 && j == 0){
                    j--;
                    continue;
                }
                SquareCoordinates squareCoordinates = new SquareCoordinates(j, i);
                getSquare(squareCoordinates).setSquareContent(SquareContent.BLACK_MAN);
                blackPieces.add(new Man(PieceColor.BLACK, getSquare(squareCoordinates)));
            }
        }
    }

    public Square[][] getBoardSquares() {
        return this.boardSquares;
    }

    public List<Piece> getPieces(PieceColor pieceColor) {
        if(pieceColor == PieceColor.WHITE) {
            return getWhitePieces();
        } else if(pieceColor == PieceColor.BLACK) {
            return getBlackPieces();
        } else {
            throw new IllegalArgumentException("PieceColor is not valid");
        }
    }

    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Square[] getLastRow(PieceColor pieceColor) throws PieceColorException {
        //LastRow is the row in which a Man becomes a King
        Square[] lastRow = new Square[8];
        if(pieceColor == PieceColor.WHITE) {
            for(int i = 0; i < 8; i++) {
                lastRow[i] = boardSquares[i][7];
            }
        } else if (pieceColor == PieceColor.BLACK) {
            for(int i = 0; i < 8; i++) {
                lastRow[i] = boardSquares[i][0];
            }
        } else {
            throw new PieceColorException("Board.getLastRow(...) does not accept this PieceColor");
        }
        return lastRow;
    }

    public Square getSquare(SquareCoordinates squareCoordinates){
        if(squareCoordinates.getColumn() < 0 || squareCoordinates.getColumn() >= 8
                || squareCoordinates.getRow()<0 || squareCoordinates.getRow()>=8){
            throw new RuntimeException();
        }
        return this.boardSquares[squareCoordinates.getColumn()][squareCoordinates.getRow()];
    }
    @Override
    public String toString() {
        return toStringForWhitePlayer();
    }

    public String toStringFor(PlayerColor playerColor) throws PlayerColorException {
        if(playerColor.equals(PlayerColor.WHITE)) {
            return toStringForWhitePlayer();
        } else if((playerColor.equals(PlayerColor.BLACK))) {
            return toStringForBlackPlayer();
        } else {
            throw new PlayerColorException("Board.toStringFor(...) does not accept this PlayerColor");
        }
    }

    private String toStringForWhitePlayer() {
        StringBuilder stringBuilder = new StringBuilder();

        for(byte i=7; i>=0; i--) {
            stringBuilder.append(i+1);
            stringBuilder.append("\t");
            for(byte j=0; j<8; j++) {
                try {
                    SquareCoordinates squareCoordinates = new SquareCoordinates(j, i);
                    stringBuilder.append(getSquare(squareCoordinates).getSquareContent().toString());
                    stringBuilder.append("\t");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            stringBuilder.append(System.lineSeparator());
        }
        //stringBuilder.append(System.lineSeparator());
        stringBuilder.append("\t A \t B \t C \t D \t E \t F \t G \t H");
        return stringBuilder.toString();
    }

    private String toStringForBlackPlayer() {
        StringBuilder stringBuilder = new StringBuilder();
        for(byte i=0; i<8; i++) {
            stringBuilder.append(i+1);
            stringBuilder.append("\t");
            for(byte j=7; j>=0; j--) {
                try {
                    SquareCoordinates squareCoordinates = new SquareCoordinates(j,i);
                    stringBuilder.append(getSquare(squareCoordinates).getSquareContent().toString());
                    stringBuilder.append("\t");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            stringBuilder.append(System.lineSeparator());
        }
        stringBuilder.append("\t H \t G \t F \t E \t D \t C \t B \t A");
        return stringBuilder.toString();
    }

    public List<Square> getReachableSquares(Piece piece) { //reachable or adjacent
        Square pieceSquare = piece.getSquare();
        int pieceSquareX = pieceSquare.getSquareCoordinates().getColumn(); // 0-based columns (instead of A, B, ...)
        int pieceSquareY = pieceSquare.getSquareCoordinates().getRow(); // 0-based rows (instead of 1, 2, ...)
        List<Square> squaresList = new ArrayList<>();
        // loop through the 4 corner-adjacent squares
        for(short i=-1; i<=1; i+=2){ // i to move on a row
            for(short j=-1; j<=1; j+=2){ // j to move on a column
                if(pieceSquareY+i < 0 || pieceSquareY+i >= 8) // rows -1 and 8 do not exist => continue
                    continue;
                if(pieceSquareX+j < 0 || pieceSquareX+j >= 8) // columns (A-1) and (H+1) do not exist => continue
                    continue;
                if(piece.isMan()) {
                    if( (piece.getColor().equals(PieceColor.WHITE)) && (i < 0) )
                        continue;
                    if( (piece.getColor().equals(PieceColor.BLACK)) && (i > 0) )
                        continue;
                }
                squaresList.add(this.boardSquares[pieceSquareX+j][pieceSquareY+i]);
            }
        }
        return squaresList;
    }

}

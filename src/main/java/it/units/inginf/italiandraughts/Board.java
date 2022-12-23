package it.units.inginf.italiandraughts;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Square[][] boardSquares;
    private int numberWhitePiece;
    private int numberBlackPiece;

    public Board() throws Exception {
        boardSquares = new Square[8][8];
        for(byte i=0; i<8; i++){
            for(byte j=0; j<8; j++) {
                boardSquares[i][j] = new Square(i, j);
            }
            System.out.println();
        }
        numberWhitePiece = 0;
        numberBlackPiece = 0;
        initBoard();
    }

    private void initBoard() throws Exception {
        // add White initial pieces (rows 1, 2, 3)
        for(byte i=0; i<3; i++){
            for(byte j=0; j<8; j+=2){
                if(i % 2 == 0 && j == 0){
                    j--;
                    continue;
                }
                this.boardSquares[i][j].setSquareContent(SquareContent.WHITE_MAN);
                numberWhitePiece++;
            }
        }
        // add Black initial pieces (rows 6, 7, 8)
        for(byte i=5; i<8; i++){
            for(byte j=0; j<8; j+=2){
                if(i % 2 == 0 && j == 0){
                    j--;
                    continue;
                }
                this.boardSquares[i][j].setSquareContent(SquareContent.BLACK_MAN);
                numberBlackPiece++;
            }
            System.out.println();
        }
    }

    public Square[][] getBoardSquares() {
        return boardSquares;
    }
    
    public int getNumberWhitePieces() {
        return this.numberWhitePiece;
    }

    public int getNumberBlackPieces() {
        return this.numberBlackPiece;
    }

    public int getNumberOfPieces(PieceColor color) {
        if(color == PieceColor.WHITE) {
            return getNumberWhitePieces();
        } else {
            return getNumberBlackPieces();
        }
    }

    public Square[] getLastRow(PieceColor color) {//LastRow is the row in which a Man becomes a King
        if(color == PieceColor.WHITE) {
            return boardSquares[7];
        } else {
            return boardSquares[0];
        }
    }

    public Square getSquare(int matrixCoordinateX, int matrixCoordinateY){
        if(matrixCoordinateX<0 || matrixCoordinateX>=8
                || matrixCoordinateY<0 || matrixCoordinateY>=8){
            throw new RuntimeException();
        }
        return this.boardSquares[matrixCoordinateX][matrixCoordinateY];
    }

    public List<Square> getReachableSquares(Piece piece) { //reachable or adjacent
        Square pieceSquare = piece.getSquare();
        int pieceSquareX = pieceSquare.getMatrixCoordinateX(); // 0-based columns (instead of A, B, ...)
        int pieceSquareY = pieceSquare.getMatrixCoordinateY(); // 0-based rows (instead of 1, 2, ...)
        List<Square> squaresList = new ArrayList<>();
        // loop through the 4 corner-adjacent squares
        for(short i=-1; i<=1; i+=2){ // i to move on a row
            for(short j=-1; j<=1; j+=2){ // j to move on a column
                if(pieceSquareY+i < 0 || pieceSquareY+i >= 8) // rows -1 and 8 do not exist => continue
                    continue;
                if(pieceSquareX+j < 0 || pieceSquareX+j >= 8) // columns (A-1) and (H+1) do not exist => continue
                    continue;
                if(!(piece instanceof King)) {
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
    
    public void decrementNumberWhitePieces() throws Exception {
        if(numberWhitePiece > 0) {
            numberWhitePiece--;
        } else {
            throw new Exception("It is not possible to have less than 0 pieces");
        }
    }

    public void decrementNumberBlackPieces() throws Exception {
        if(numberBlackPiece > 0) {
            numberBlackPiece--;
        } else {
            throw new Exception("It is not possible to have less than 0 pieces");
        }
    }

}

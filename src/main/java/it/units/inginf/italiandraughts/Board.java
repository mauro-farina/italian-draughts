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
    
    public String getDisplayBoardForWhitePlayer() {
        String displayBoard = "";
        for(int i=7; i>=0; i--){
            for(int j=0; j<8; j++){
                displayBoard = displayBoard+ boardSquares[i][j].getSquareContent().toString();
            }
            displayBoard = displayBoard + "\n";
        }
        return displayBoard;
    }

    public String getDisplayBoardForBlackPlayer() {
        String displayBoard= "";
        for(int i=0; i<8; i++){
            for(int j=7; j>=0; j--){
                displayBoard = displayBoard+ boardSquares[i][j].getSquareContent().toString();
            }
            displayBoard = displayBoard + "\n";
        }
        return displayBoard;
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

    public Square getSquare(int x, int y){
        if(x<0 || x>=8 || y<0 || y>=8){
            throw new RuntimeException();
        }
        return this.boardSquares[x][y];
    }

    public List<Square> getReachableSquares(Piece piece) { //reachable or adjacent
        Square pieceSquare = piece.getSquare();
        int pieceSquareX = pieceSquare.getMatrixCoordinateX();
        int pieceSquareY = pieceSquare.getMatrixCoordinateY();
        List<Square> squaresList = new ArrayList<>();
        for(short i=-1; i<=1; i+=2){ // loop through the 4 corner-adjacent squares
            for(short j=-1; j<=1; j+=2){
                if(pieceSquareY+i < 0 || pieceSquareY+i >= 8) // rows -1 and 8 do not exist => continue
                    continue;
                if(pieceSquareX+j < 0 || pieceSquareX+j >= 8) // columns (A-1) and (H+1) do not exist => continue
                    continue;
                if(piece instanceof Man) {
                    if( (piece.getColor().equals(PieceColor.WHITE)) && (i > 0) )
                        continue;
                    if( (piece.getColor().equals(PieceColor.BLACK)) && (i < 0) )
                        continue;
                }
                squaresList.add(this.boardSquares[pieceSquareY+i][pieceSquareX+j]);
            }
        }
        return squaresList;
    }

    public ArrayList<Square> getAdjacentSquares(Man man) throws Exception {
        ArrayList<Square> squareList = new ArrayList<>();
        if(man.color == PieceColor.WHITE) {
            if(man.getSquare().getCoordinateY().equals("8")) {
                throw new Exception("this man no longer exists");
            } else {
                switch(man.getSquare().getCoordinateX()) {
                    case "A":
                        squareList.add(boardSquares[1][Integer.parseInt(man.getSquare().getCoordinateY())]);
              /*
A Man on column A only borders column B, identified by the index 1.
The row index of an adjacent cell would be the index of the square where the man is located + 1.
But the integer value returned by getCoordinateY() is already greater than 1 since the row index of board starts
from 1 and not from 0. So the row index used is the integer value of getCoordinateY() + 1 - 1 = integer value
of getCoordinateY().
               */
                        break;
                    case "B":
                        squareList.add(boardSquares[0][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[2][Integer.parseInt(man.getSquare().getCoordinateY())]);
              /*
Column B borders on columns A and C identified respectively by the indexes 0 and 2.
              */
                        break;
                    case "C":
                        squareList.add(boardSquares[1][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[3][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    case "D":
                        squareList.add(boardSquares[2][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[4][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    case "E":
                        squareList.add(boardSquares[3][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[5][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    case "F":
                        squareList.add(boardSquares[4][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[6][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    case "G":
                        squareList.add(boardSquares[5][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        squareList.add(boardSquares[7][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    case "H":
                        squareList.add(boardSquares[6][Integer.parseInt(man.getSquare().getCoordinateY())]);
                        break;
                    default:
                        throw new Exception("this square does not exists");
                }
            }
        } else { //this.color = PieceColor.Black
            if(man.getSquare().getCoordinateY().equals("1")) {
                throw new Exception("this man no longer exists");
            } else {
                switch(man.getSquare().getCoordinateX()) {
                    case "A":
                        squareList.add(boardSquares[1][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
              /*
In this case the row index is given by the index of the square where the considered Man is located - 1.
Since the integer value of getCoordinateY() is 1 higher than the square index then an adjacent square will have
as integer value of getCoordinateY() - 1 - 1.
              */
                        break;
                    case "B":
                        squareList.add(boardSquares[0][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[2][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "C":
                        squareList.add(boardSquares[1][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[3][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "D":
                        squareList.add(boardSquares[2][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[4][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "E":
                        squareList.add(boardSquares[3][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[5][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "F":
                        squareList.add(boardSquares[4][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[6][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "G":
                        squareList.add(boardSquares[5][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        squareList.add(boardSquares[7][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    case "H":
                        squareList.add(boardSquares[6][Integer.parseInt(man.getSquare().getCoordinateY()) - 2]);
                        break;
                    default:
                        throw new Exception("this square does not exists");
                }
            }

        }
        return squareList;
    }
    
    public ArrayList<Square> getAdjacentSquares(King king) throws Exception {
        ArrayList<Square> squareList = new ArrayList<>();
        if(king.getSquare().getCoordinateY().equals("1")) {
            switch(king.getSquare().getCoordinateX()) {
                case "A":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "B":
                    squareList.add(boardSquares[0][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "C":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "D":
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "E":
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "F":
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "G":
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[7][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                case "H":
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    break;
                default:
                    throw new Exception("this square does not exists");
            }
        } else if(king.getSquare().getCoordinateY().equals("8")) {
            switch(king.getSquare().getCoordinateX()) {
                case "A":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "B":
                    squareList.add(boardSquares[0][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "C":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "D":
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "E":
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "F":
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "G":
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[7][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "H":
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                default:
                    throw new Exception("this square does not exists");
            }
        } else {
            switch(king.square.getCoordinateX()) {
                case "A":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "B":
                    squareList.add(boardSquares[0][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[0][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "C":
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[1][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "D":
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[2][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "E":
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[3][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "F":
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[4][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "G":
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[7][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[5][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    squareList.add(boardSquares[7][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                case "H":
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY())]);
                    squareList.add(boardSquares[6][Integer.parseInt(king.getSquare().getCoordinateY()) - 2]);
                    break;
                default:
                    throw new Exception("this square does not exists");
            }
        }
        return squareList;
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

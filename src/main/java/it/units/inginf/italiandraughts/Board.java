package it.units.inginf.italiandraughts;

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
                this.boardSquares[i][j].updateSquare(SquareContent.WHITE_MAN);
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
                this.boardSquares[i][j].updateSquare(SquareContent.BLACK_MAN);
                numberBlackPiece++;
            }
            System.out.println();
        }
    }
    
}

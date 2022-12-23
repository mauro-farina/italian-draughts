package it.units.inginf.italiandraughts;

public class BoardDrawer implements CLIReader {

    private final Board board;

    public BoardDrawer(Board board) {
        this.board = board;
    }

    @Override
    public void printBoard(Board board) {
        printBoardForWhitePlayer(); // print from White POV as default
    }

    @Override
    public void printBoardForPlayer(Board board, PlayerColor playerColor) {
        if(playerColor.equals(PlayerColor.WHITE)) {
            printBoardForWhitePlayer();
        } else {
            printBoardForBlackPlayer();
        }
    }

    private void printBoardForWhitePlayer(){
        for(byte i=7; i>=0; i--){
            for(byte j=0; j<8; j++){
                System.out.print(this.board.getBoardSquares()[i][j].toString());
            }
            System.out.println();
        }
    }

    private void printBoardForBlackPlayer(){
        for(byte i=0; i<8; i++){
            for(byte j=7; j>=0; j--){
                System.out.print(this.board.getBoardSquares()[i][j].toString());
            }
            System.out.println();
        }
    }

}

package it.units.inginf.italiandraughts;


public class Game {

    private Board board;

    Turn turn; //turn = WHITE if it's turn of the white player, otherwise turn = BLACK

    private int turnCounter;

    public Game() throws Exception {
        board = new Board();
        turnCounter = 1;
        turn = Turn.WHITE;
    }

    public Board getBoard() {
        return this.board;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }

    public void incrementTurnCounter() {
        this.turnCounter++;
    }

    public void changeTurn() throws Exception {
        if(this.turn == null) {
            throw new Exception("Turn cannot be null");
        } else if(this.turn == Turn.WHITE) {
            this.turn = Turn.BLACK;
        } else {
            this.turn = Turn.WHITE;
        }
        incrementTurnCounter();
    }

    public boolean checkVictoryCondition() {
        if((board.getNumberWhitePieces() == 0) || (board.getNumberBlackPieces() == 0)) {
            return true;
        } else {
            return false;
        }
    }

}

package it.units.inginf.italiandraughts;


public class Game {

    private Board board;

    private PlayerColor isTheTurnOfPlayer; //color = WHITE if it's turn of the white player, otherwise color = BLACK

    private int turnCounter;

    public Game() throws Exception {
        board = new Board();
        turnCounter = 1;
        isTheTurnOfPlayer = PlayerColor.WHITE;
    }

    public Board getBoard() {
        return this.board;
    }

    public PlayerColor getIsTheTurnOfPlayer() {
        return this.isTheTurnOfPlayer;
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }

    public void incrementTurnCounter() {
        this.turnCounter++;
    }

    public void changeTurn() throws Exception {
        if(this.isTheTurnOfPlayer == null) {
            throw new Exception("Turn cannot be null");
        } else if(this.isTheTurnOfPlayer == PlayerColor.WHITE) {
            this.isTheTurnOfPlayer = PlayerColor.BLACK;
        } else {
            this.isTheTurnOfPlayer = PlayerColor.WHITE;
        }
        incrementTurnCounter();
    }

    public boolean checkVictoryCondition() {
        if((board.getWhitePieces().size() == 0) || (board.getBlackPieces().size() == 0)) {
            return true;
        } else {
            return false;
        }
    }

}

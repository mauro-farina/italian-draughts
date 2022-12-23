package it.units.inginf.italiandraughts;


public class Game {

    private final Board board;

    private PlayerColor turn; //= WHITE if it's turn of the white player, otherwise = BLACK

    private int turnCounter;

    public Game() throws Exception {
        board = new Board();
        turnCounter = 1;
        turn = PlayerColor.WHITE;
    }

    public Board getBoard() {
        return this.board;
    }

    public PlayerColor getTurn() {
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
        } else if(this.turn == PlayerColor.WHITE) {
            this.turn = PlayerColor.BLACK;
        } else {
            this.turn = PlayerColor.WHITE;
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

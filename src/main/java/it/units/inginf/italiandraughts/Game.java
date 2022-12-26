package it.units.inginf.italiandraughts;


public class Game {

    private final Board board;

    private final Player player1; //This is the white player
    
    private final Player player2; //This is the black player
    
    private Player currentTurn;

    private int turnCounter;

    public Game() throws Exception {
        if((player1 == null) || (player2 == null)) {
            throw new Exception("One or both players are not valid");
        } else if((player1.getColor() != PlayerColor.WHITE) || (player2.getColor() != PlayerColor.BLACK)) {
            throw new Exception("player1 uses the white pieces, while player2 uses the black pieces");
        } else {
            this.board = new Board();
            this.turnCounter = 1;
            this.player1 = player1;
            this.player2 = player2;
            this.currentTurn = this.player1;
        }
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentTurn() {
        return this.currentTurn;
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }
    
     public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void incrementTurnCounter() {
        this.turnCounter++;
    }

    public void changeTurn() throws Exception {
        if(this.currentTurn == player1) {
            this.currentTurn = player2;
        } else if(this.currentTurn == player2){
            this.currentTurn = player1;
        } else {
            throw new Exception("currentTurn cannot be null");
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

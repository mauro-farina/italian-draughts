package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.CommandParser;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.commands.Command;
import it.units.inginf.italiandraughts.commands.CommandRunner;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;
import it.units.inginf.italiandraughts.io.InputReader;
import it.units.inginf.italiandraughts.io.OutputPrinter;

public class Game {

    private final Board board;
    private final Player player1; //This is the white player
    private final Player player2; //This is the black player
    private Player currentTurn;
    private Player winnerPlayer;
    private int turnCounter;
    private final InputReader inputReader; // could go in the Main to separate concerns
    private final OutputPrinter outputPrinter; // could go in the Main to separate concerns
    private GameState gameState;
    private final CommandRunner commandRunner;

    public Game(Player player1, Player player2) throws Exception {
        if((player1 == null) || (player2 == null)) {
            throw new Exception("One or both players are not valid");
        } else if((player1.getColor() != PlayerColor.WHITE) || (player2.getColor() != PlayerColor.BLACK)) {
            throw new Exception("player1 uses the white pieces, while player2 uses the black pieces");
        } else {
            this.gameState = GameState.SETTING_UP;
            this.board = new Board();
            this.player1 = player1;
            this.player2 = player2;
            this.inputReader = new CommandLineInputReader();
            this.outputPrinter = new CommandLineOutputPrinter();
            this.commandRunner = new CommandRunner(this);
        }
    }

    public void start() {
        initGame();
        //execute command HELP
        while(this.gameState == GameState.PLAYING) {
            outputPrinter.print(board.toStringFor(PlayerColor.WHITE));
            outputPrinter.print("Turn of " + getCurrentTurn().getNickname());
            while(true) {
                String readCommand = inputReader.readInput();
                try {
                    Command command = CommandParser.parseCommand(readCommand);
                    commandRunner.runCommand(command);
                    break;
                } catch (Exception exception) {
                    outputPrinter.print(exception.getMessage());
                }
            }
        }
    }

    public void initGame() {
        this.gameState = GameState.PLAYING;
        this.currentTurn = this.player1;
        this.turnCounter = 1;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentTurn() {
        return this.currentTurn;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(Player player) throws Exception {
        if((player != player1) && (player != player2)) {
            throw new Exception("The winner player does not exists");
        } else {
            winnerPlayer = player;
            gameState = GameState.OVER;
        }
    }

    public int getTurnCounter() {
        return this.turnCounter;
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
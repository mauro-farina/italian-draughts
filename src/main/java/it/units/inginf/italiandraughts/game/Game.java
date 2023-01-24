package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.CommandParser;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.*;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareNameException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.io.InputReader;
import it.units.inginf.italiandraughts.io.OutputPrinter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    private final Board board;
    private final Player player1; //This is the white player
    private final Player player2; //This is the black player
    private Player currentTurn;
    private Player winnerPlayer;
    private int turnCounter;
    private final InputReader inputReader;
    private final OutputPrinter outputPrinter;
    private GameState gameState;
    private final CommandRunner commandRunner;

    public Game(Player player1, Player player2, InputReader inputReader, OutputPrinter outputPrinter) throws PlayerException,
            SquareNameException, SquareContentException, CoordinatesException,
            PieceColorException, SquareException {
        if((player1 == null) || (player2 == null)) {
            throw new PlayerException("Game.Game() does not accept one or both players");
        } else if((player1.getColor() != PlayerColor.WHITE) || (player2.getColor() != PlayerColor.BLACK)) {
            throw new PlayerException("Game.game() does not accept one or both player's color." +
                    "\n Player1 uses the white pieces, while player2 uses the black pieces");
        } else {
            this.gameState = GameState.SETTING_UP;
            this.board = new Board();
            this.player1 = player1;
            this.player2 = player2;
            this.inputReader = inputReader;
            this.outputPrinter = outputPrinter;
            this.commandRunner = new CommandRunner(this);
        }
    }

    public void start() {
        initGame();
        List<List<CommandCapture>> obligatoryCaptureList = new ArrayList<>();
        //outputPrinter.print(obligatoryCaptureList.toString());
        try {
            commandRunner.runCommand(new CommandHelp());
            outputPrinter.print(System.lineSeparator());
        } catch (Exception exception) {
            outputPrinter.print(exception.getMessage());
        }
        while(this.gameState == GameState.PLAYING) {
            Command command = null;
            try{
                outputPrinter.print(board.toStringFor(getCurrentTurn().getColor()));
                outputPrinter.print("Turn of " + getCurrentTurn().getNickname());
                obligatoryCaptureList.addAll(ObligatoryCapture.getObligatoryCaptureList(this.getBoard(), this.currentTurn));
                if(obligatoryCaptureList.size() > 0) {
                    outputPrinter.print("Mandatory captures found:");
                    Iterator<List<CommandCapture>> captureIterator = obligatoryCaptureList.iterator();
                    while(captureIterator.hasNext()) {
                        printObligatoryCaptureList(captureIterator.next(), 0);
                        if(captureIterator.hasNext()) {
                            outputPrinter.print("or...");
                        }
                    }
                }
            } catch (Exception exception) {
                outputPrinter.print(exception.getMessage());
            }
            while(true) {
                String readCommand;
                try {
                    if(obligatoryCaptureList.size() > 0) {
                        List<CommandCapture> chosenCapturesOption = null;
                        for(short i = 0; i < obligatoryCaptureList.get(0).size(); i++) {
                            readCommand = inputReader.readInput();
                            command = CommandParser.parseCommand(readCommand);
                            if(command.getCommandType() == CommandType.HELP) {
                                commandRunner.runCommand(command);
                                i--;
                            }
                            if(command.getCommandType() == CommandType.SURRENDER) {
                                commandRunner.runCommand(command);
                                break;
                            }
                            if(command.getCommandType() == CommandType.TO) {
                                outputPrinter.print("Invalid command to, read the obligatory capture list.");
                                outputPrinter.print("Enter a new command.");
                                i--;
                            }

                            if(command.getCommandType() == CommandType.CAPTURE) {
                                if(i == 0) {
                                    for(List<CommandCapture> commandCaptureList : obligatoryCaptureList) {
                                        if(commandCaptureList.get(0).equals(command)) {
                                            chosenCapturesOption = commandCaptureList;
                                            break;
                                        }
                                    }
                                    if(chosenCapturesOption == null) {
                                        outputPrinter.print("Invalid capture, read the obligatory capture list.");
                                        i--;
                                        continue;
                                    }
                                }
                                //program does not reach here until chosenCapturesOption is valued
                                assert chosenCapturesOption != null;
                                if(command.equals(chosenCapturesOption.get(i))) {
                                    commandRunner.runCommand(command);
                                    if(i < chosenCapturesOption.size()-1) {
                                        outputPrinter.print(board.toStringFor(getCurrentTurn().getColor()));
                                        outputPrinter.print("Next obligatory captures:");
                                        printObligatoryCaptureList(chosenCapturesOption, i+1);
                                    }
                                } else {
                                    outputPrinter.print("Invalid command: " + command);
                                    outputPrinter.print("Expected command: " + chosenCapturesOption.get((i)));
                                    i--;
                                }
                            }
                        }
                    } else {
                        readCommand = inputReader.readInput();
                        command = CommandParser.parseCommand(readCommand);
                        commandRunner.runCommand(command);
                    }
                    obligatoryCaptureList.clear();
                    break;
                } catch (Exception exception) {
                    outputPrinter.print(exception.getMessage());
                }
            }
            try {
                if(checkVictoryCondition()) {
                    setWinnerPlayer(this.currentTurn);
                    outputPrinter.print("The winner is " + this.currentTurn.getNickname());
                } else if(checkDrawCondition()) {
                    this.gameState = GameState.OVER;
                    changeTurn();
                    outputPrinter.print("The game ends in a draw: none of " +
                            this.currentTurn.getNickname() + "'s pieces can move");
                } else if(command.getCommandType() != CommandType.HELP) {
                    changeTurn();
                    outputPrinter.print("");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void printObligatoryCaptureList(List<CommandCapture> obligatoryCaptureList, int excludeCapturesBeforeIndex) {
        for(int i=excludeCapturesBeforeIndex; i<obligatoryCaptureList.size(); i++) {
            outputPrinter.print(obligatoryCaptureList.get(i).toString());
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
        return this.winnerPlayer;
    }

    public void setWinnerPlayer(Player player) throws PlayerException {
        if((player != player1) && (player != player2)) {
            throw new PlayerException("Game.setWinnerPlayer() the entered player cannot be the winner player");
        } else {
            this.winnerPlayer = player;
            this.gameState = GameState.OVER;
        }
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }

    public void incrementTurnCounter() {
        this.turnCounter++;
    }

    public void changeTurn() throws PlayerException {
        if(this.currentTurn == player1) {
            this.currentTurn = player2;
        } else if(this.currentTurn == player2){
            this.currentTurn = player1;
        } else {
            throw new PlayerException("Player.changeTurn() currentTurn cannot be null");
        }
        incrementTurnCounter();
    }

    public boolean checkVictoryCondition() {
        return board.getWhitePieces().size() == 0 || board.getBlackPieces().size() == 0;
    }

    public boolean checkDrawCondition() throws BoardException, SquareException, PlayerException {
        PieceColor pieceColor;
        if (this.currentTurn == this.player1) {
            pieceColor = PieceColor.BLACK;
        } else if (this.currentTurn == this.player2) {
            pieceColor = PieceColor.WHITE;
        } else {
            throw new PlayerException("Invalid turn");
        }
        for (Piece piece : this.board.getPieces(pieceColor)) {
            for (Square reachableSquare : this.board.getReachableSquares(piece)) {
                if (reachableSquare.isFree()) {
                    return false;
                } else {
                    try {
                        if (new ExecutableCommandCapture(this.board,
                                piece.getSquare().getSquareCoordinates(),
                                reachableSquare.getSquareCoordinates()).isValid()) {
                            return false;
                        }
                    } catch(CoordinatesException e) {
                        // piece is on the edge -> capture command returns CoordinatesException
                    }
                }
            }
        }
        return true;
    }

}

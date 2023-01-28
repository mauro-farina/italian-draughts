package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.CommandParser;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.commands.CommandRunner;
import it.units.inginf.italiandraughts.commands.CommandHelp;
import it.units.inginf.italiandraughts.commands.CommandType;
import it.units.inginf.italiandraughts.commands.Command;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PlayerColorException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareNameException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.CommandException;
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
        List<CommandCaptureList> obligatoryCaptureList = new ArrayList<>();
        try {
            commandRunner.runCommand(new CommandHelp());
            outputPrinter.print(System.lineSeparator());
        } catch (Exception exception) {
            outputPrinter.print(exception.getMessage());
        }
        while(this.gameState == GameState.PLAYING) {
            try{
                outputPrinter.print(board.toStringFor(this.currentTurn.getColor()));
                outputPrinter.print("Turn of " + this.currentTurn.getNickname());
                obligatoryCaptureList.addAll(ObligatoryCapture.getObligatoryCaptureList(this.board, this.currentTurn));
                if(obligatoryCaptureList.size() > 0) {
                    outputPrinter.print("Mandatory captures found:");
                    Iterator<CommandCaptureList> captureIterator = obligatoryCaptureList.iterator();
                    while(captureIterator.hasNext()) {
                        outputPrinter.print(captureIterator.next().toString());
                        if(captureIterator.hasNext()) {
                            outputPrinter.print("or...");
                        }
                    }
                    handleObligatoryCaptures(obligatoryCaptureList);
                    obligatoryCaptureList.clear();
                } else {
                    Command command;
                    do {
                        String readCommand = inputReader.readInput();
                        command = CommandParser.parseCommand(readCommand);
                        commandRunner.runCommand(command);
                    } while (command.getCommandType() == CommandType.HELP);
                }
            } catch (CommandException | CoordinatesException | SquareNameException | SquareException exception) {
                outputPrinter.print(exception.getMessage());
                continue;
            } catch (PlayerException | BoardException | PieceException | PieceColorException
                     | SquareContentException | PlayerColorException exception) {
                outputPrinter.print("An error occurred, game cannot be resumed.", true);
                this.gameState = GameState.OVER;
                break;
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
                }
                changeTurn();
            } catch (PlayerException | BoardException | SquareException e) {
                outputPrinter.print("An error occurred, game cannot be resumed.", true);
                this.gameState = GameState.OVER;
                break;
            }
        }
    }

    private void handleObligatoryCaptures(List<CommandCaptureList> obligatoryCaptureList)
            throws SquareNameException, CoordinatesException, CommandException, PlayerException, SquareContentException,
            PieceColorException, SquareException, BoardException, PieceException, PlayerColorException {
        List<CommandCaptureList> chosenCapturesOptions = new ArrayList<>();
        for (short i = 0; i < obligatoryCaptureList.get(0).size(); i++) {
            String readCommand = inputReader.readInput();
            Command command = CommandParser.parseCommand(readCommand);
            if (command.getCommandType() == CommandType.HELP) {
                commandRunner.runCommand(command);
                i--;
            }
            if (command.getCommandType() == CommandType.SURRENDER) {
                commandRunner.runCommand(command);
                return;
            }
            if (command.getCommandType() == CommandType.TO) {
                outputPrinter.print("Invalid command, read the obligatory capture list.");
                i--;
            }

            if (command.getCommandType() == CommandType.CAPTURE) {

                for (CommandCaptureList commandCaptureList : obligatoryCaptureList) {
                    if (commandCaptureList.get(i).equals(command)) {
                        chosenCapturesOptions.add(commandCaptureList);
                    } else {
                        chosenCapturesOptions.remove(commandCaptureList);
                    }
                }
                if (chosenCapturesOptions.isEmpty()) {
                    outputPrinter.print("Invalid capture, read the obligatory capture list.");
                    i--;
                    continue;
                }

                for (CommandCaptureList validOption : chosenCapturesOptions) {
                    if (command.equals(validOption.get(i))) {
                        commandRunner.runCommand(command);
                        if (i < validOption.size() - 1) {
                            outputPrinter.print(board.toStringFor(this.currentTurn.getColor()));
                            outputPrinter.print("Next obligatory captures:");
                            StringBuilder nextCapturesOptions = new StringBuilder();
                            for (CommandCaptureList _validOption : chosenCapturesOptions) {
                                if (command.equals(validOption.get(i))) {
                                    nextCapturesOptions.append(_validOption.subList(i+1, _validOption.size()));
                                    nextCapturesOptions.append(System.lineSeparator());
                                    nextCapturesOptions.append("or...");
                                    nextCapturesOptions.append(System.lineSeparator());
                                }
                            }
                            nextCapturesOptions.delete(nextCapturesOptions.length() - 8, nextCapturesOptions.length());
                            outputPrinter.print(nextCapturesOptions.toString());
                        }
                        break;
                    }

                    outputPrinter.print("Invalid command: " + command);
                    outputPrinter.print("Expected command: " + chosenCapturesOptions);
                    i--;
                }
            }
        }
    }

    public void initGame() {
        this.gameState = GameState.PLAYING;
        this.currentTurn = this.player1;
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
    
    public GameState getGameState() {
        return this.gameState;
    }

    public void changeTurn() throws PlayerException {
        if(this.currentTurn == player1) {
            this.currentTurn = player2;
        } else if(this.currentTurn == player2){
            this.currentTurn = player1;
        } else {
            throw new PlayerException("Player.changeTurn() currentTurn cannot be null");
        }
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
            throw new PlayerException("Game.checkDrawCondition() does not accept this player");
        }
        for (Piece piece : this.board.getPieces(pieceColor)) {
            for (Square reachableSquare : this.board.getReachableSquares(piece)) {
                if (reachableSquare.isFree()) {
                    return false;
                } else {
                    try {
                        if (new CommandCapture(
                                piece.getSquare().getSquareCoordinates(),
                                reachableSquare.getSquareCoordinates()).isValid(this.board)) {
                            return false;
                        }
                    } catch(CoordinatesException e) {
                        // piece is on the edge -> capture command returns CoordinatesException
                    } catch (CommandException e) {
                        // capture is not valid -> isValid returns CommandException
                    }
                }
            }
        }
        return true;
    }

    public OutputPrinter getOutputPrinter() {
        return this.outputPrinter;
    }

}

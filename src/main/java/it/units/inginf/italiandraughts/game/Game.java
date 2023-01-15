package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.CommandParser;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.Command;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.commands.CommandRunner;
import it.units.inginf.italiandraughts.commands.CommandType;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareNameException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;
import it.units.inginf.italiandraughts.io.InputReader;
import it.units.inginf.italiandraughts.io.OutputPrinter;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Board board;
    private final Player player1; //This is the white player
    private final Player player2; //This is the black player
    private Player currentTurn;
    private Player winnerPlayer;
    private int turnCounter;
    private final InputReader inputReader; // could go in the it.units.inginf.italiandraughts.Main to separate concerns
    private final OutputPrinter outputPrinter; // could go in the it.units.inginf.italiandraughts.Main to separate concerns
    private GameState gameState;
    private final CommandRunner commandRunner;

    public Game(Player player1, Player player2) throws PlayerException,
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
            this.inputReader = new CommandLineInputReader();
            this.outputPrinter = new CommandLineOutputPrinter();
            this.commandRunner = new CommandRunner(this);
        }
    }

    public void start() {
        initGame();
        List<CommandCapture> obligatoryCapture = new ArrayList<>();
        try {
            commandRunner.runCommand(CommandParser.parseCommand( "help"));
            outputPrinter.print("\n");
        } catch (Exception exception) {
            outputPrinter.print(exception.getMessage());
        }
        while(this.gameState == GameState.PLAYING) {
            Command command;
            try{
                outputPrinter.print(board.toStringFor(getCurrentTurn().getColor()));
                outputPrinter.print("Turn of " + getCurrentTurn().getNickname());
                obligatoryCapture.addAll(ObligatoryCapture.getObligatoryCaptureList(this));
                if(obligatoryCapture.size() > 0) {
                    outputPrinter.print("this is a obligatory capture list, make them all");
                    for (CommandCapture commandCapture: obligatoryCapture) {
                        outputPrinter.print(
                                this.board.getSquare(commandCapture.getFromCoordinates())
                                        .getSquareName()
                                        .toString()
                                        + " capture "
                                        + this.board.getSquare(commandCapture.getPieceToCaptureCoordinates())
                                        .getSquareName()
                                        .toString());
                    }
                }
            } catch (Exception exception) {
                outputPrinter.print(exception.getMessage());
            }
            while(true) {
                String readCommand = inputReader.readInput();
                try {
                    command = CommandParser.parseCommand(readCommand);
                    commandRunner.runCommand(command);
                    /*
                    if(obligatoryCapture != null && obligatoryCapture.size() > 0) {
                        for(int i = 0; i < obligatoryCapture.size(); i++) {
                            readCommand = inputReader.readInput();
                            command = CommandParser.parseCommand(readCommand);
                            if(command.getCommandType() == CommandType.HELP ||
                                    command.getCommandType() == CommandType.SURRENDER) {
                                commandRunner.runCommand(command);
                            }
                            if(command.getCommandType() == CommandType.TO) {
                                outputPrinter.print("Invalid command, read the obligatory capture list");
                                i--;
                            }
                            if(command.getCommandType() == CommandType.CAPTURE) {
                                String[] readCommandSplit = readCommand.split(" ");
                                if((obligatoryCapture.get(i)
                                        .getFromCoordinates()
                                        .toString()
                                        .equalsIgnoreCase("(" + readCommandSplit[0] + ")"))
                                && (obligatoryCapture.get(i)
                                        .getPieceToCaptureCoordinates()
                                        .toString()
                                        .equalsIgnoreCase("(" + readCommandSplit[2] + ")"))) {
                                    commandRunner.runCommand(command);
                                } else {
                                    outputPrinter.print("Invalid command, read the obligatory capture list");
                                    i--;
                                }
                            }
                        }
                        if(command.getCommandType() == CommandType.CAPTURE) {
                            CommandCapture commandCapture = CommandParser.parseCommandCapture(readCommand);
                            lastMovedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(
                                    commandCapture.getToCoordinates()));
                        }
                    } else {
                        command = CommandParser.parseCommand(readCommand);
                        commandRunner.runCommand(command);
                        if(command.getCommandType() == CommandType.TO) {
                            CommandTo commandTo = CommandParser.parseCommandTo(readCommand);
                            lastMovedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(
                                    commandTo.getToCoordinates()));
                        }
                        if(command.getCommandType() == CommandType.CAPTURE) {
                            CommandCapture commandCapture = CommandParser.parseCommandCapture(readCommand);
                            lastMovedPiece = BoardUtils.researchPiece(this.board, this.board.getSquare(
                                    commandCapture.getToCoordinates()));
                        }
                    } */
                    obligatoryCapture.clear();
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
                    outputPrinter.print("The game ends in a draw: none of " +
                            this.currentTurn.getNickname() + "'s pieces can move");
                } else if(command.getCommandType() != CommandType.HELP) {
                    changeTurn();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        if(board.getWhitePieces().size() == 0 || board.getBlackPieces().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDrawCondition() throws CoordinatesException, BoardException, SquareException {
        if(this.currentTurn == this.player1) {
            for(Piece piece: this.board.getWhitePieces()) {
                for(Square reachableSquare: this.board.getReachableSquares(piece)) {
                    if(reachableSquare.isFree()) {
                        return false;
                    } else {
                        if(new SingleCapture(this.board,
                                piece.getSquare().getSquareCoordinates(),
                                reachableSquare.getSquareCoordinates()).isValid()) {
                            return false;
                        }
                    }
                }
            }
        } else if (this.currentTurn == this.player2) {
            for(Piece piece: this.board.getBlackPieces()) {
                for(Square reachableSquare: this.board.getReachableSquares(piece)) {
                    if(reachableSquare.isFree()) {
                        return false;
                    } else {
                        if(new SingleCapture(this.board,
                                piece.getSquare().getSquareCoordinates(),
                                reachableSquare.getSquareCoordinates()).isValid()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}

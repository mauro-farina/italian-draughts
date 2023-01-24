package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;
import it.units.inginf.italiandraughts.io.OutputPrinter;
import it.units.inginf.italiandraughts.exception.CommandException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.BoardException;

import java.util.List;

public class CommandRunner {

    private final Game game;
    private final OutputPrinter outputPrinter;

    public CommandRunner(Game game) {
        this.game = game;
        this.outputPrinter = new CommandLineOutputPrinter();
    }

    public void runCommand(Command command) throws PlayerException, CommandException, BoardException, PieceException, PieceColorException, SquareException, SquareContentException {
        if(command == null) {
            throw new CommandException("Command cannot be null");
        }
        if(command.getCommandType().equals(CommandType.SURRENDER)) {
            this.runCommandSurrender();
        } else if(command.getCommandType().equals(CommandType.HELP)) {
            this.runCommandHelp();
        } else if(command.getCommandType().equals(CommandType.TO)) {
            this.runCommandTo(
                    ((CommandTo) command).getFromCoordinates(),
                    ((CommandTo) command).getToCoordinates()
            );
        } else if(command.getCommandType().equals(CommandType.CAPTURE)) {
            this.runCommandCapture((CommandCapture) command);
        }
    }

    private void runCommandSurrender() throws PlayerException {
        if(game.getCurrentTurn() == game.getPlayer1()) {
            game.setWinnerPlayer(game.getPlayer2());
        } else {
            game.setWinnerPlayer(game.getPlayer1());
        }
        outputPrinter.print("The winner is " + game.getWinnerPlayer().getNickname());
    }

    private void runCommandHelp() {
        outputPrinter.print("Type 'help' to get command instructions");
        outputPrinter.print(System.lineSeparator());
        outputPrinter.print("Type 'sur' or 'surrender' to surrender");
        outputPrinter.print(System.lineSeparator());
        outputPrinter.print("To move a piece from its square B3 to a free square A4, type 'B3 to A4'");
        outputPrinter.print(System.lineSeparator());
        outputPrinter.print("To capture a piece located on square C4 with your piece in B3 type 'B3 capture C4'.");
        outputPrinter.print("You can shorten 'capture' with 'capt' or 'cap'");
    }

    private void runCommandTo(SquareCoordinates coordinatesStartingSquare, SquareCoordinates coordinatesArrivalSquare) throws  BoardException, PieceException, PieceColorException, SquareException, SquareContentException {
        Square startingSquare = game.getBoard().getSquare(coordinatesStartingSquare);
        Square arrivalSquare = game.getBoard().getSquare(coordinatesArrivalSquare);
        Piece selectedPiece = BoardUtils.researchPiece(this.game.getBoard(), startingSquare);
        if(selectedPiece == null) {
            throw new PieceException("CommandRunner.runCommandTo()," +
                    "\n no piece located on " + startingSquare.getSquareName().toString());
        }
        if(!selectedPiece.getColor().toString().equalsIgnoreCase(game.getCurrentTurn().getColor().toString())) {
            throw new PieceException("CommandRunner.runCommandTo() -> cannot move opponent pieces");
        }
        if(!arrivalSquare.isFree()) {
            throw new SquareException("CommandRunner.runCommandTo(), " +
                    "\n there already is a piece on " + arrivalSquare.getSquareName().toString());
        }
        List<Square> listReachableSquares = game.getBoard().getReachableSquares(selectedPiece);
        for(int i = 0; i < listReachableSquares.size(); i++) {
            if(arrivalSquare == listReachableSquares.get(i)) {
                break;
            } else if (i == listReachableSquares.size() - 1) { // ???
                throw new SquareException("CommandRunner.runCommandTo() does not accept arrivalSquare");
            }
        }
        if(!listReachableSquares.contains(arrivalSquare)) {
            throw new SquareException("CommandRunner.runCommandTo() -> cannot reach " +
                    arrivalSquare.getSquareName().toString() + " from " + startingSquare.getSquareName().toString());
        }
        startingSquare.setSquareContent(SquareContent.EMPTY);
        arrivalSquare.setSquareContent(selectedPiece);
        selectedPiece.setSquare(arrivalSquare);
        if(selectedPiece.isMan()) {
            if (game.getBoard().getLastRow(selectedPiece.getColor())[0].getSquareCoordinates().getRow() == arrivalSquare.getSquareCoordinates().getRow()) {
                BoardUtils.toCrown(this.game.getBoard(), selectedPiece);
            }
        }
    }

    private void runCommandCapture(CommandCapture commandCapture) throws BoardException, PieceException, PieceColorException, SquareException, SquareContentException, CommandException {
        Board board = game.getBoard();
        if(!commandCapture.isValid(board)) {
            return;
        }
        Square selectedPieceSquare = board.getSquare(commandCapture.getFromCoordinates());
        Square capturedPieceSquare = board.getSquare(commandCapture.getPieceToCaptureCoordinates());
        Square destinationSquare = board.getSquare(commandCapture.getToCoordinates());
        Piece selectedPiece = BoardUtils.researchPiece(board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(board, capturedPieceSquare);
        assert selectedPiece != null; // command is valid -> pieces are not null
        if(!selectedPiece.getColor().toString().equals(game.getCurrentTurn().getColor().toString())) {
            throw new CommandException("Cannot move opponent pieces");
        }
        selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
        capturedPieceSquare.setSquareContent(SquareContent.EMPTY);
        destinationSquare.setSquareContent(selectedPiece);
        BoardUtils.removePiece(board, capturedPiece);
        selectedPiece.setSquare(destinationSquare);
        if(selectedPiece.isMan()) {
            if (board.getLastRow(selectedPiece.getColor())[0].getSquareCoordinates().getRow() == destinationSquare.getSquareCoordinates().getRow()) {
                BoardUtils.toCrown(board, selectedPiece);
            }
        }
    }
    
}

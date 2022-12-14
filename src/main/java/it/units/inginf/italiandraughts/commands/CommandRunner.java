package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;
import it.units.inginf.italiandraughts.io.OutputPrinter;
import it.units.inginf.italiandraughts.exception.CommandException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
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

    public void runCommand(Command command) throws PlayerException, CommandException, CoordinatesException, BoardException, PieceException, PieceColorException, SquareException, SquareContentException {
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
            this.runCommandCapture(
                    ((CommandCapture) command).getFromCoordinates(),
                    ((CommandCapture) command).getPieceToCaptureCoordinates(),
                    ((CommandCapture) command).getToCoordinates()
            );
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
        if(selectedPiece.isMan()) {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                arrivalSquare.setSquareContent(SquareContent.WHITE_MAN);
            } else {
                arrivalSquare.setSquareContent(SquareContent.BLACK_MAN);
            }
        } else { // isKing
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                arrivalSquare.setSquareContent(SquareContent.WHITE_KING);
            } else {
                arrivalSquare.setSquareContent(SquareContent.BLACK_KING);
            }
        }
        selectedPiece.setSquare(arrivalSquare);
        if(game.getBoard().getLastRow(selectedPiece.getColor())[0].getSquareCoordinates().getCoordinateY() == arrivalSquare.getSquareCoordinates().getCoordinateY()) {
            BoardUtils.toCrown(this.game.getBoard(), selectedPiece);
        }
    }

    private void runCommandCapture(SquareCoordinates coordinatesSelectedPieceSquare, SquareCoordinates coordinatesCapturedPieceSquare, SquareCoordinates coordinatesDestinationSquare) throws CommandException, CoordinatesException, BoardException, PieceException, PieceColorException, SquareException, SquareContentException {
        Board board = game.getBoard();
        PieceColor selectedPieceColor;
        Piece selectedPiece;
        Piece capturedPiece;
        Square selectedPieceSquare = board.getSquare(coordinatesSelectedPieceSquare);
        Square capturedPieceSquare = board.getSquare(coordinatesCapturedPieceSquare);
        Square destinationSquare = board.getSquare(coordinatesDestinationSquare);
        if (game.getCurrentTurn().getColor() == PlayerColor.WHITE) {
            selectedPieceColor = PieceColor.WHITE;
        } else if (game.getCurrentTurn().getColor() == PlayerColor.BLACK) {
            selectedPieceColor = PieceColor.BLACK;
        } else {
            throw new PieceColorException("CommandRunner.runCommandCapture() -> invalid turn");
        }
        selectedPiece = BoardUtils.researchPiece(this.game.getBoard(), selectedPieceSquare);
        capturedPiece = BoardUtils.researchPiece(this.game.getBoard(), capturedPieceSquare);
        if (selectedPiece != null && capturedPiece != null) {
            if ((selectedPiece.getColor() != selectedPieceColor) && (capturedPiece.getColor() == selectedPieceColor)
                    && (capturedPieceSquare.isFree()) && (!destinationSquare.isFree())) {
                throw new CoordinatesException("CommandRunner.runCommandCapture() does not accept the coordinates " +
                        "of capturedPieceSquare and destinationSquare");
            }
            if(selectedPiece.isMan() && capturedPiece.isKing()) {
                throw  new CommandException("CommandRunner.runCommandCapture(), a man does not capture a king");
            }
            List<Square> listReachableSquaresOfSelectedPiece = board.getReachableSquares(selectedPiece);
            int pieceIndex = -1;
            for (int i = 0; i < listReachableSquaresOfSelectedPiece.size(); i++) {
                if (capturedPieceSquare == listReachableSquaresOfSelectedPiece.get(i)) {
                    pieceIndex = i;
                    break;
                }
            }
            if (pieceIndex == -1) {
                throw new CommandException("CommandRunner.runCommandCapture() does not accept the Command");
            }
            selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
            capturedPieceSquare.setSquareContent(SquareContent.EMPTY);
            BoardUtils.removePiece(this.game.getBoard(), capturedPiece);
            if (selectedPiece.isMan()) {
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    destinationSquare.setSquareContent(SquareContent.WHITE_MAN);
                } else {
                    destinationSquare.setSquareContent(SquareContent.BLACK_MAN);
                }
            } else { // isKing
                if (selectedPiece.getColor() == PieceColor.WHITE) {
                    destinationSquare.setSquareContent(SquareContent.WHITE_KING);
                } else {
                    destinationSquare.setSquareContent(SquareContent.BLACK_KING);
                }
            }
            selectedPiece.setSquare(destinationSquare);
            if (game.getBoard().getLastRow(selectedPiece.getColor())[0].getSquareCoordinates().getCoordinateY() == destinationSquare.getSquareCoordinates().getCoordinateY()) {
                BoardUtils.toCrown(this.game.getBoard(), selectedPiece);
            }
        } else {
            throw new CommandException("CommandRunner.runCommandCapture() does not accept the Command");
        }
    }
    
}

package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.*;
import it.units.inginf.italiandraughts.board.*;

import java.util.List;

public class CommandManager {

    private final Game game;

    public CommandManager(Game game) {
        this.game = game;
    }

    public void runCommand(Command command) throws Exception {
        if(command instanceof CommandSurrender){
            this.runCommandSurrender();
        }
        if(command instanceof CommandHelp){
            this.runCommandHelp();
        }
        if(command instanceof CommandTo){
            this.runCommandTo(
                    ((CommandTo) command).getFromCoordinates(),
                    ((CommandTo) command).getToCoordinates()
            );
        }
        if(command instanceof CommandCapture){
            this.runCommandCapture(
                    ((CommandCapture) command).getFromCoordinates(),
                    ((CommandCapture) command).getPieceToCaptureCoordinates()
            );
        }
    }

    private void runCommandSurrender() throws Exception {
        if(game.getCurrentTurn() == game.getPlayer1()) {
            game.setWinnerPlayer(game.getPlayer2());
        } else {
            game.setWinnerPlayer(game.getPlayer1());
        }
        System.out.println("The winner is " + game.getWinnerPlayer().getNickname());
    }

    private void runCommandHelp() {
        System.out.println("Type 'help' to get command instructions");
        System.out.println("Type 'sur' or 'surrender' to surrender");
        System.out.println("To move a piece from its square B3 to a free square A4, type 'B3 to A4'");
        System.out.println("To capture a piece located on square C4 with your piece in B3 type 'B3 capture C4'. You can shorten 'capture' with 'capt' or 'cap'");
    }

    private void runCommandTo(int[] coordinatesStartingSquare, int[] coordinatesArrivalSquare) throws Exception {
        PieceColor selectedPieceColor;
        Piece selectedPiece;
        Square startingSquare = game.getBoard().getSquare(coordinatesStartingSquare[0], coordinatesStartingSquare[1]);
        Square arrivalSquare = game.getBoard().getSquare(coordinatesArrivalSquare[0], coordinatesArrivalSquare[1]);
        if(game.getCurrentTurn().getColor() == PlayerColor.WHITE) {
            selectedPieceColor = PieceColor.WHITE;
        } else if (game.getCurrentTurn().getColor() == PlayerColor.BLACK) {
            selectedPieceColor = PieceColor.BLACK;
        } else {
            throw new Exception("Invalid turn");
        }
        selectedPiece = game.getBoard().researchPiece(startingSquare);
        if((selectedPiece.getColor() != selectedPieceColor) && (!arrivalSquare.isFree())) {
            throw new Exception("Invalid coordinates");
        }
        List<Square> listReachableSquares = game.getBoard().getReachableSquares(selectedPiece);
        int pieceIndex = -1;
        for(int i = 0; i < listReachableSquares.size() - 1; i++) {
            if(arrivalSquare == listReachableSquares.get(i)) {
                pieceIndex = i;
                break;
            } else if (i == listReachableSquares.size() - 1) {
                throw new Exception("Invalid arrival square");
            }
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
        if(Integer.parseInt(game.getBoard().getLastRow(selectedPiece.getColor())[0].getCoordinateY()) - 1 == arrivalSquare.getMatrixCoordinateY()) {
            game.getBoard().manBecomesKing(selectedPiece.getColor(), pieceIndex);
        }
    }

    private void runCommandCapture(int[] coordinatesSelectedPieceSquare, int[] coordinatesCapturedPieceSquare) throws Exception {
        Board board = game.getBoard();
        PieceColor selectedPieceColor;
        Piece selectedPiece;
        Piece capturedPiece;
        Square selectedPieceSquare = board.getSquare(coordinatesSelectedPieceSquare[0], coordinatesSelectedPieceSquare[1]);
        Square capturedPieceSquare = board.getSquare(coordinatesCapturedPieceSquare[0], coordinatesCapturedPieceSquare[1]);
        int[] coordinatesDestinationSquare = new int[2];
        if(coordinatesSelectedPieceSquare[0] - 1 == coordinatesCapturedPieceSquare[0]) {
            coordinatesDestinationSquare[0] = coordinatesCapturedPieceSquare[0] - 1;
        } else if (coordinatesSelectedPieceSquare[0] + 1 == coordinatesCapturedPieceSquare[0]) {
            coordinatesDestinationSquare[0] = coordinatesCapturedPieceSquare[0] + 1;
        } else {
            throw new Exception("Invalid coordinates, capture not possible");
        }
        if(coordinatesSelectedPieceSquare[1] - 1 == coordinatesCapturedPieceSquare[1]) {
            coordinatesDestinationSquare[1] = coordinatesCapturedPieceSquare[1] - 1;
        } else if (coordinatesSelectedPieceSquare[1] + 1 == coordinatesCapturedPieceSquare[1]) {
            coordinatesDestinationSquare[1] = coordinatesCapturedPieceSquare[1] + 1;
        } else {
            throw new Exception("Invalid coordinates, capture not possible");
        }
        Square destinationSquare = board.getSquare(coordinatesDestinationSquare[0], coordinatesDestinationSquare[1]);
        if(game.getCurrentTurn().getColor() == PlayerColor.WHITE) {
            selectedPieceColor = PieceColor.WHITE;
        } else if (game.getCurrentTurn().getColor() == PlayerColor.BLACK) {
            selectedPieceColor = PieceColor.BLACK;
        } else {
            throw new Exception("Invalid turn");
        }
        selectedPiece = board.researchPiece(selectedPieceSquare);
        capturedPiece = board.researchPiece(capturedPieceSquare);
        if((selectedPiece.getColor() != selectedPieceColor) && (capturedPiece.getColor() == selectedPieceColor)
                && (capturedPieceSquare.isFree()) && (!destinationSquare.isFree())) {
            throw new Exception("Invalid coordinates");
        }
        List<Square> listReachableSquaresOfSelectedPiece = board.getReachableSquares(selectedPiece);
        int pieceIndex = -1;
        for(int i = 0; i < listReachableSquaresOfSelectedPiece.size() - 1; i++) {
            if(capturedPieceSquare == listReachableSquaresOfSelectedPiece.get(i)) {
                pieceIndex = i;
                break;
            } else if (i == listReachableSquaresOfSelectedPiece.size() - 1) {
                throw new Exception("Invalid arrival square");
            }
        }
        selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
        if(selectedPiece.isMan()) {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_MAN);
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_MAN);
            }
        } else { // isKing
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_KING);
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_KING);
            }
        }
        selectedPiece.setSquare(capturedPieceSquare);
        this.runCommandTo(coordinatesCapturedPieceSquare, coordinatesDestinationSquare);
    }
}

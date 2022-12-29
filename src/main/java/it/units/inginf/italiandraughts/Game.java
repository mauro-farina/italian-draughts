package it.units.inginf.italiandraughts;

import java.util.List;

public class Game {

    private final Board board;
    private final Player player1; //This is the white player
    private final Player player2; //This is the black player
    private Player currentTurn;
    private Player winnerPlayer;
    private int turnCounter;

    public Game(Player player1, Player player2) throws Exception {
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

    public void runCommand(Command command) throws Exception {
        if(command.getCommandType() == CommandType.SURRENDER) {
            this.runCommandSurrender();
        } else if(command.getCommandType() == CommandType.HELP) {
            this.runCommandHelp();
        } else if(command.getCommandType() == CommandType.TO) {
            this.runCommandTo(command.getCoordinatesStartingSquare(), command.getCoordinatesArrivalSquare());
        } else if(command.getCommandType() == CommandType.CAPTURE) {
            this.runCommandCapture(command.getCoordinatesStartingSquare(), command.getCoordinatesArrivalSquare());
        } else {
            throw new Exception("Invalid command");
        }
    }

    private void runCommandSurrender() {
        if(this.getCurrentTurn() == this.player1) {
            winnerPlayer = player2;
        } else {
            winnerPlayer = player1;
        }
        System.out.println("The winner is " + winnerPlayer.getNickname());
    }

    private void runCommandHelp() {
        System.out.println("Type help to get command instructions");
        System.out.println("Type sur or surrender to surrender");
        System.out.println("To move a piece from its square X to a free square Y, type X to Y. X and Y are the coordinates of a square, for example A2");
        System.out.println("To capture a piece located on square Y type X cap Y or X capt Y or X capture Y. X is the square of the piece that captures");
    }

    private void runCommandTo(int[] coordinatesStartingSquare, int[] coordinatesArrivalSquare) throws Exception {
        PieceColor selectedPieceColor;
        Piece selectedPiece;
        Square startingSquare = board.getSquare(coordinatesStartingSquare[0], coordinatesStartingSquare[1]);
        Square arrivalSquare = board.getSquare(coordinatesArrivalSquare[0], coordinatesArrivalSquare[1]);
        if(this.currentTurn.getColor() == PlayerColor.WHITE) {
            selectedPieceColor = PieceColor.WHITE;
        } else if (this.currentTurn.getColor() == PlayerColor.BLACK) {
            selectedPieceColor = PieceColor.BLACK;
        } else {
            throw new Exception("Invalid turn");
        }
        selectedPiece = board.researchPiece(startingSquare);
        if((selectedPiece.getColor() != selectedPieceColor) && (!arrivalSquare.isFree())) {
            throw new Exception("Invalid coordinates");
        }
        List<Square> listReachableSquares = board.getReachableSquares(selectedPiece);
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
        if(Integer.parseInt(board.getLastRow(selectedPiece.getColor())[0].getCoordinateY()) - 1 == arrivalSquare.getMatrixCoordinateY()) {
            board.manBecomesKing(selectedPiece.getColor(), pieceIndex);
        }
    }

    private void runCommandCapture(int[] coordinatesSelectedPieceSquare, int[] coordinatesCapturedPieceSquare) throws Exception {
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
        if(this.currentTurn.getColor() == PlayerColor.WHITE) {
            selectedPieceColor = PieceColor.WHITE;
        } else if (this.currentTurn.getColor() == PlayerColor.BLACK) {
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

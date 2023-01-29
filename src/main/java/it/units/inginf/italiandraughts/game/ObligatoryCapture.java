package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.King;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PlayerColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.CommandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObligatoryCapture {

    public static List<CommandCaptureList> getObligatoryCaptureList(Board board, Player currentTurn) throws BoardException, SquareContentException,
            PieceColorException, SquareException, PieceException, PlayerException, PlayerColorException, CoordinatesException {
        if (currentTurn == null) {
            throw new PlayerException("ObligatoryCapture.getObligatoryCaptureList() does not accept " +
                    "this player");
        }
        if (currentTurn.getColor() == null) {
            throw new PlayerColorException("ObligatoryCapture.getObligatoryCaptureList() does not accept " +
                    "this PlayerColor");
        }
        PieceColor piecesColor = currentTurn.getColor() == PlayerColor.WHITE ? PieceColor.WHITE : PieceColor.BLACK;
        List<Piece> piecesList = board.getPieces(piecesColor);
        // `score` is used to determine the mandatory list of captures that a player must perform
        // e.g. "the first and foremost rule to obey is to capture the greatest quantity of pieces"
        Map<List<TemporaryCapture>, Integer> capturesListScore = new HashMap<>();

        for (Piece piece : piecesList) {
            for(List<TemporaryCapture> capturesList : getCapturesListOptionsForPiece(board, piece)) {
                if (capturesList.isEmpty())
                    continue;
                capturesListScore.put(capturesList, getCapturesListScore(capturesList));
            }
        }

        // capturesList(s) with the highest score is(are) the mandatory sequence of captures a player must perform
        int maxScore = capturesListScore.values().stream().max(Integer::compare).orElse(0);

        // keep only capture options with the highest score (same number of captures, numb. kings captured, ...)
        List<List<TemporaryCapture>> obligatoryCaptureOptionsList = capturesListScore
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();

        // cast List<List<ExecutableCommandCapture>> to List<List<CommandCapture>> and return
        return obligatoryCaptureOptionsList
                .stream()
                .map(optionsList -> optionsList
                        .stream()
                        .map(TemporaryCapture::getCommandCapture)
                        .toList())
                .map(CommandCaptureList::new)
                .toList();
    }

    private static List<List<TemporaryCapture>> getCapturesListOptionsForPiece(Board board, Piece piece)
            throws SquareContentException, PieceColorException, SquareException, BoardException, PieceException, CoordinatesException {
        if(piece == null) return null;
        return capturesListOptionsWorker(board, piece, new ArrayList<>(), new ArrayList<>());
    }

    private static List<List<TemporaryCapture>> capturesListOptionsWorker(Board board, Piece piece, List<TemporaryCapture> capturesList, List<List<TemporaryCapture>> capturesListOptions) throws SquareException, BoardException, SquareContentException, PieceColorException, PieceException, CoordinatesException {
        for(Square square: board.getReachableSquares(piece)) {
            TemporaryCapture temporaryCapture;
            try {
                temporaryCapture = new TemporaryCapture(
                        board,
                        new CommandCapture(
                                piece.getSquare().getSquareCoordinates(),
                                square.getSquareCoordinates())
                );
            } catch(CoordinatesException e) {
                // reachable square is on the edge -> impossible to capture
                continue;
            }
            if (temporaryCapture.isValid()) {
                temporaryCapture.run(); // execute capture
                // new updatedCapturesList = capturesList
                List<TemporaryCapture> updatedCapturesList = new ArrayList<>(capturesList);
                // add capture just executed to updateCapturesList
                updatedCapturesList.add(temporaryCapture);
                // recursion on the updatedCapturesList (allows to explore all capture options from a given piece)
                capturesListOptionsWorker(board, piece, updatedCapturesList, capturesListOptions);
                // undo the capture
                temporaryCapture.reset();
            }
        }
        if(!capturesList.isEmpty())
            capturesListOptions.add(capturesList); // add sequence of captures to the captures options of the given piece
        return capturesListOptions;
    }

    private static int getCapturesListScore(List<TemporaryCapture> capturesList) throws SquareException, BoardException, CoordinatesException {
        if(capturesList.isEmpty()) {
            return 0;
        }
        // the first and foremost rule to obey is to capture the greatest quantity of pieces.
        int score = 1000 * capturesList.size();
        // If a player may capture an equal number of pieces with either a man or king, they must do so with the king
        if(capturesList.get(0).isCapturingPieceKing()) {
            score += 500;
        }
        // If a player may capture an equal number of pieces with a king, they must capture the greatest number of kings possible.
        score += 100 * getNumberOfCapturedKing(capturesList);
        // If a player may capture an equal number of pieces (each series containing a king) with a king,
        // they must capture wherever the king occurs first
        for(TemporaryCapture temporaryCapture : capturesList) {
            if(!temporaryCapture.isCapturedPieceKing()) {
                score -= 1;
            } else {
                break;
            }
        }
        return score;
    }


    private static int getNumberOfCapturedKing(List<TemporaryCapture> singleCaptureList) throws BoardException, SquareException, CoordinatesException {
        int numberOfCapturedKing = 0;
        for(TemporaryCapture temporaryCapture : singleCaptureList) {
            if(temporaryCapture.isCapturedPieceKing()) {
                numberOfCapturedKing++;
            }
        }
        return numberOfCapturedKing;
    }

}

class TemporaryCapture {

    private final Board board;
    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates pieceToCaptureCoordinates;
    private final SquareCoordinates toCoordinates;
    private final CommandCapture commandCapture;
    private boolean capturedPieceIsKing;

    public TemporaryCapture(Board board, CommandCapture commandCapture) {
        this.board = board;
        this.commandCapture = commandCapture;
        this.fromCoordinates = commandCapture.getFromCoordinates();
        this.toCoordinates = commandCapture.getToCoordinates();
        this.pieceToCaptureCoordinates = commandCapture.getPieceToCaptureCoordinates();
    }

    public CommandCapture getCommandCapture() {
        return this.commandCapture;
    }

    public boolean isValid() throws SquareException, BoardException {
        try {
            return commandCapture.isValid(board);
        } catch (CommandException e) {
            return false;
        }
    }

    public void run() throws BoardException, SquareException, SquareContentException,
            PieceColorException, PieceException, CoordinatesException {
        Square selectedPieceSquare = board.getSquare(this.fromCoordinates);
        Square capturedPieceSquare = board.getSquare(this.pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(this.toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, selectedPieceSquare);
        Piece capturedPiece = BoardUtils.researchPiece(this.board, capturedPieceSquare);
        if(selectedPiece != null && capturedPiece != null) {
            this.capturedPieceIsKing = capturedPiece.isKing();
            selectedPieceSquare.setSquareContent(SquareContent.EMPTY);
            capturedPieceSquare.setSquareContent(SquareContent.EMPTY);
            BoardUtils.removePiece(this.board, capturedPiece);
            destinationSquare.setSquareContent(selectedPiece);
            selectedPiece.setSquare(destinationSquare);
        }
    }

    public void reset() throws BoardException, SquareException, SquareContentException,
            PieceColorException, PieceException, CoordinatesException {
        Square selectedPieceSquare = board.getSquare(fromCoordinates);
        Square capturedPieceSquare = board.getSquare(pieceToCaptureCoordinates);
        Square destinationSquare = board.getSquare(toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(this.board, destinationSquare);
        if(selectedPiece == null) {
            return;
        }
        destinationSquare.setSquareContent(SquareContent.EMPTY);
        selectedPieceSquare.setSquareContent(selectedPiece);
        selectedPiece.setSquare(selectedPieceSquare);
        if(capturedPieceIsKing) {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_KING);
                this.board.getBlackPieces().add(new King(PieceColor.BLACK, capturedPieceSquare));
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_KING);
                this.board.getWhitePieces().add(new King(PieceColor.WHITE, capturedPieceSquare));
            }
        } else {
            if(selectedPiece.getColor() == PieceColor.WHITE) {
                capturedPieceSquare.setSquareContent(SquareContent.BLACK_MAN);
                this.board.getBlackPieces().add(new Man(PieceColor.BLACK, capturedPieceSquare));
            } else {
                capturedPieceSquare.setSquareContent(SquareContent.WHITE_MAN);
                this.board.getWhitePieces().add(new Man(PieceColor.WHITE, capturedPieceSquare));
            }
        }
    }

    public boolean isCapturingPieceKing() throws BoardException, SquareException, CoordinatesException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.fromCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

    public boolean isCapturedPieceKing() throws BoardException, SquareException, CoordinatesException {
        Piece piece = BoardUtils.researchPiece(this.board, this.board.getSquare(this.pieceToCaptureCoordinates));
        if(piece == null) {
            return false;
        }
        return piece.isKing();
    }

}

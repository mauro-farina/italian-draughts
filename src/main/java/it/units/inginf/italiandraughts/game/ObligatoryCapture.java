package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.commands.ExecutableCommandCapture;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;
import it.units.inginf.italiandraughts.exception.PlayerColorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObligatoryCapture {

    public static List<List<CommandCapture>> getObligatoryCaptureList(Board board, Player currentTurn) throws BoardException, SquareContentException,
            PieceColorException, SquareException, PieceException, PlayerException, PlayerColorException {
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
        Map<List<ExecutableCommandCapture>, Integer> capturesListScore = new HashMap<>();

        for (Piece piece : piecesList) {
            for(List<ExecutableCommandCapture> capturesList : getCapturesListOptionsForPiece(board, piece)) {
                if (capturesList.isEmpty())
                    continue;
                capturesListScore.put(capturesList, getCapturesListScore(capturesList));
            }
        }

        // capturesList(s) with the highest score is(are) the mandatory sequence of captures a player must perform
        int maxScore = capturesListScore.values().stream().max(Integer::compare).orElse(0);

        // keep only capture options with the highest score (same number of captures, numb. kings captured, ...)
        List<List<ExecutableCommandCapture>> obligatoryCaptureOptionsList = capturesListScore
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
                        .map(capture -> (CommandCapture) capture)
                        .toList())
                .toList();
    }

    private static List<List<ExecutableCommandCapture>> getCapturesListOptionsForPiece(Board board, Piece piece)
            throws SquareContentException, PieceColorException, SquareException, BoardException, PieceException {
        if(piece == null) return null;
        return capturesListOptionsWorker(board, piece, new ArrayList<>(), new ArrayList<>());
    }

    private static List<List<ExecutableCommandCapture>> capturesListOptionsWorker(Board board, Piece piece, List<ExecutableCommandCapture> capturesList, List<List<ExecutableCommandCapture>> capturesListOptions) throws SquareException, BoardException, SquareContentException, PieceColorException, PieceException {
        for(Square square: board.getReachableSquares(piece)) {
            ExecutableCommandCapture singleCapture;
            try {
                singleCapture = new ExecutableCommandCapture(
                        board,
                        piece.getSquare().getSquareCoordinates(),
                        square.getSquareCoordinates()
                );
            } catch(CoordinatesException e) {
                // reachable square is on the edge -> impossible to capture
                continue;
            }
            if (singleCapture.isValid()) {
                singleCapture.run(); // execute capture
                // new updatedCapturesList = capturesList
                List<ExecutableCommandCapture> updatedCapturesList = new ArrayList<>(capturesList);
                // add capture just executed to updateCapturesList
                updatedCapturesList.add(singleCapture);
                // recursion on the updatedCapturesList (allows to explore all capture options from a given piece)
                capturesListOptionsWorker(board, piece, updatedCapturesList, capturesListOptions);
                // undo the capture
                singleCapture.reset();
            }
        }
        if(!capturesList.isEmpty())
            capturesListOptions.add(capturesList); // add sequence of captures to the captures options of the given piece
        return capturesListOptions;
    }

    private static int getCapturesListScore(List<ExecutableCommandCapture> capturesList) throws SquareException, BoardException {
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
        for(ExecutableCommandCapture singleCapture : capturesList) {
            if(!singleCapture.isCapturedPieceKing()) {
                score -= 1;
            } else {
                break;
            }
        }
        return score;
    }
/*
    private static void compareTwoLists(List<ExecutableCommandCapture> singleCaptureList, List<ExecutableCommandCapture> newSingleCaptureList) throws BoardException, SquareException {
        if(newSingleCaptureList.size() == 0 || newSingleCaptureList.size() < singleCaptureList.size()) {
            return;
        }
        if (newSingleCaptureList.size() > singleCaptureList.size()) {
            //first check number of captured pieces
            singleCaptureList.clear();
            singleCaptureList.addAll(newSingleCaptureList);
            return;
        }
        // newSingleCaptureList.size() == singleCaptureList.size()
        if (!singleCaptureList.get(0).isCapturingPieceKing()
                    && newSingleCaptureList.get(0).isCapturingPieceKing()) {
            //second check start piece is a king
            singleCaptureList.clear();
            singleCaptureList.addAll(newSingleCaptureList);
        } else if (singleCaptureList.get(0).isCapturingPieceKing()
                    && newSingleCaptureList.get(0).isCapturingPieceKing()) {
            int numberOfKingInSingleCaptureList = getNumberOfCapturedKing(singleCaptureList);
            int numberOfKingInNewSingleCaptureList = getNumberOfCapturedKing(newSingleCaptureList);
            if (numberOfKingInNewSingleCaptureList > numberOfKingInSingleCaptureList) {
                //third check number of captured king
                singleCaptureList.clear();
                singleCaptureList.addAll(newSingleCaptureList);
            } else if ((numberOfKingInNewSingleCaptureList == numberOfKingInSingleCaptureList)
                    && (numberOfKingInNewSingleCaptureList > 0)) {
                for (short i = 0; i < singleCaptureList.size(); i++) {
                    if ((newSingleCaptureList.get(i).isCapturedPieceKing())
                                && (!singleCaptureList.get(i).isCapturedPieceKing())) {
                        //last check closer king
                        singleCaptureList.clear();
                        singleCaptureList.addAll(newSingleCaptureList);
                    }
                }
            }
        }
    }

    private static void fillSingleCaptureList(Board board, List<ExecutableCommandCapture> singleCaptureList, Piece piece)
            throws SquareContentException, PieceColorException, SquareException, BoardException, PieceException {
        if(piece == null) {
            return;
        }
        for(Square square: board.getReachableSquares(piece)) {
            ExecutableCommandCapture singleCapture;
            try {
                singleCapture = new ExecutableCommandCapture(
                        board,
                        piece.getSquare().getSquareCoordinates(),
                        square.getSquareCoordinates()
                );
            } catch(CoordinatesException e) {
                continue;
            }

            List<ExecutableCommandCapture> newSingleCaptureList = new ArrayList<>(singleCaptureList);
            if (singleCapture.isValid()) {
                newSingleCaptureList.add(singleCapture);
                singleCapture.run();
                compareTwoLists(singleCaptureList, newSingleCaptureList);
                fillSingleCaptureList(
                        board,
                        singleCaptureList,
                        BoardUtils.researchPiece(
                                board,
                                board.getSquare(singleCapture.getToCoordinates())
                        ));
                singleCapture.reset();
            }
        }
    }
*/

    private static int getNumberOfCapturedKing(List<ExecutableCommandCapture> singleCaptureList) throws BoardException, SquareException {
        int numberOfCapturedKing = 0;
        for(ExecutableCommandCapture singleCapture : singleCaptureList) {
            if(singleCapture.isCapturedPieceKing()) {
                numberOfCapturedKing++;
            }
        }
        return numberOfCapturedKing;
    }

}

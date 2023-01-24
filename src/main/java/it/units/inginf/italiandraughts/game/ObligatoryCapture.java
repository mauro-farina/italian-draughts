package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.CommandCapture;
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

    public static List<List<CommandCapture>> getObligatoryCaptureList(Board board, Player currentTurn) throws BoardException, SquareContentException, CoordinatesException,
            PieceColorException, SquareException, PieceException, PlayerException, PlayerColorException {
        if (currentTurn == null) {
            throw new PlayerException("ObligatoryCapture.getObligatoryCaptureList() does not accept " +
                    "this player");
        }
        if (currentTurn.getColor() == null) {
            throw new PlayerColorException("ObligatoryCapture.getObligatoryCaptureList() does not accept " +
                    "this PlayerColor");
        }
        List<List<CommandCapture>> obligatoryCaptureOptionsList = new ArrayList<>();
        PieceColor piecesColor = currentTurn.getColor() == PlayerColor.WHITE ? PieceColor.WHITE : PieceColor.BLACK;
        List<Piece> piecesList = board.getPieces(piecesColor);
        Map<List<ExecutableCommandCapture>, Integer> mandatoryCapturesOptionsScores = new HashMap<>();
        for (Piece piece : piecesList) {
            List<ExecutableCommandCapture> mandatoryCapturesList = new ArrayList<>();
            fillSingleCaptureList(board, mandatoryCapturesList, piece);
            if(mandatoryCapturesList.isEmpty())
                continue;
            mandatoryCapturesOptionsScores.put(mandatoryCapturesList, getCapturesListScore(mandatoryCapturesList));
        }
        int maxScore = mandatoryCapturesOptionsScores.values().stream().max(Integer::compare).orElse(0);

        // keep only capture options with the highest score (same number of captures, numb. kings captured, ...)
        List<List<ExecutableCommandCapture>> mandatorySingleCapturesLists = mandatoryCapturesOptionsScores
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();
        for(List<ExecutableCommandCapture> mandatoryCaptureList : mandatorySingleCapturesLists) {
            List<CommandCapture> mandatoryCaptures = new ArrayList<>();
            for (ExecutableCommandCapture singleCapture : mandatoryCaptureList) {
                mandatoryCaptures.add(
                        new CommandCapture(
                                singleCapture.getFromCoordinates(),
                                singleCapture.getPieceToCaptureCoordinates()
                        ));
            }
            obligatoryCaptureOptionsList.add(mandatoryCaptures);
        }
        return obligatoryCaptureOptionsList;
    }

    private static int getCapturesListScore(List<ExecutableCommandCapture> singleCaptureList) throws SquareException, BoardException {
        if(singleCaptureList.isEmpty()) {
            return 0;
        }
        int score = 0;
        score += 1000 * singleCaptureList.size();
        if(singleCaptureList.get(0).isCapturingPieceKing()) {
            score += 500;
        }
        score += 100 * getNumberOfCapturedKing(singleCaptureList);
        for(ExecutableCommandCapture singleCapture : singleCaptureList) {
            if(!singleCapture.isCapturedPieceKing()) {
                score -= 1;
            } else {
                break;
            }
        }
        return score;
    }

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

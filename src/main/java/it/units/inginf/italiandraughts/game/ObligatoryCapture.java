package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.PlayerException;
import it.units.inginf.italiandraughts.exception.PieceException;
import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;
import it.units.inginf.italiandraughts.exception.SquareContentException;

import java.util.List;
import java.util.ArrayList;

public class ObligatoryCapture {

    public static List<CommandCapture> getObligatoryCaptureList(Game game) throws BoardException, SquareContentException, CoordinatesException,
            PieceColorException, SquareException, PieceException, PlayerException {
        List<CommandCapture> obligatoryCaptureList = new ArrayList<>();
        List<SingleCapture> singleCaptureList = new ArrayList<>();
        List<SingleCapture> newSingleCaptureList = new ArrayList<>();
        if(game.getCurrentTurn() == game.getPlayer1()) {
            for(Piece piece: game.getBoard().getWhitePieces()) {
                fullSingleCaptureList(game.getBoard(), newSingleCaptureList, piece);
                compareTwoLists(singleCaptureList, newSingleCaptureList);
                newSingleCaptureList.clear();
            }
        } else if(game.getCurrentTurn() == game.getPlayer2()) {
            for(Piece piece: game.getBoard().getBlackPieces()) {
                fullSingleCaptureList(game.getBoard(), newSingleCaptureList, piece);
                compareTwoLists(singleCaptureList, newSingleCaptureList);
                newSingleCaptureList.clear();
            }
        } else {
            throw new PlayerException("ObligatoryCapture.getObligatoryCaptureList():" +
                    " this method does not accept this player");
        }
        for(SingleCapture singleCapture : singleCaptureList) {
            obligatoryCaptureList.add(
                    new CommandCapture(singleCapture.getFromCoordinates(),
                            singleCapture.getPieceToCaptureCoordinates()));
        }
        return obligatoryCaptureList;
    }

    private static void compareTwoLists(List<SingleCapture> singleCaptureList, List<SingleCapture> newSingleCaptureList) throws BoardException, SquareException {
        if(newSingleCaptureList.size() > 0) {
            if (newSingleCaptureList.size() > singleCaptureList.size()) {
                //first check number of captured pieces
                singleCaptureList.clear();
                singleCaptureList.addAll(newSingleCaptureList);
            } else if (newSingleCaptureList.size() == singleCaptureList.size()) {
                if ((!captureWithKing(singleCaptureList.get(0))) && (captureWithKing(newSingleCaptureList.get(0)))) {
                    //second check start piece is a king
                    singleCaptureList.clear();
                    singleCaptureList.addAll(newSingleCaptureList);
                } else if ((captureWithKing(singleCaptureList.get(0))) && (captureWithKing(newSingleCaptureList.get(0)))) {
                    int numberOfKingInSingleCaptureList = getNumberOfCapturedKing(singleCaptureList);
                    int numberOfKingInNewSingleCaptureList = getNumberOfCapturedKing(newSingleCaptureList);
                    if (numberOfKingInNewSingleCaptureList > numberOfKingInSingleCaptureList) {
                        //third check number of captured king
                        singleCaptureList.clear();
                        singleCaptureList.addAll(newSingleCaptureList);
                    } else if ((numberOfKingInNewSingleCaptureList == numberOfKingInSingleCaptureList)
                            && (numberOfKingInNewSingleCaptureList > 0)) {
                        for (short i = 0; i < singleCaptureList.size(); i++) {
                            if ((captureKing(newSingleCaptureList.get(i))) && (!captureKing(singleCaptureList.get(i)))) {
                                //last check closer king
                                singleCaptureList.clear();
                                singleCaptureList.addAll(newSingleCaptureList);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void fullSingleCaptureList(Board board, List<SingleCapture> singleCaptureList, Piece piece)
            throws SquareContentException, CoordinatesException, PieceColorException, SquareException, BoardException, PieceException {
        if(piece != null) {
            for(Square square: board.getReachableSquares(piece)) {
                SingleCapture singleCapture = new SingleCapture(board,
                        piece.getSquare().getSquareCoordinates(),
                        square.getSquareCoordinates());
                List<SingleCapture> newSingleCaptureList = new ArrayList<>(singleCaptureList);
                if (singleCapture.isValid()) {
                    newSingleCaptureList.add(singleCapture);
                    singleCapture.run();
                    compareTwoLists(singleCaptureList, newSingleCaptureList);
                    fullSingleCaptureList(board, singleCaptureList,
                            BoardUtils.researchPiece(board,
                                    board.getSquare(singleCapture.getToCoordinates())));
                    singleCapture.runBack();
                }
            }
        }
    }

    private static boolean captureWithKing(SingleCapture singleCapture) throws BoardException, SquareException {
        return singleCapture.pieceOnFromCoordinatesIsKing();
    }

    private static boolean captureKing(SingleCapture singleCapture) throws BoardException, SquareException {
        return singleCapture.pieceOnCaptureCoordinatesIsKing();
    }

    private static int getNumberOfCapturedKing(List<SingleCapture> singleCaptureList) throws BoardException, SquareException {
        int numberOfCapturedKing = 0;
        for(SingleCapture singleCapture : singleCaptureList) {
            if(captureKing(singleCapture)) {
                numberOfCapturedKing++;
            }
        }
        return numberOfCapturedKing;
    }

}

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

    public static List<CommandCapture> getObligatoryCaptureList(Game game)
            throws BoardException, SquareContentException, CoordinatesException,
            PieceColorException, SquareException, PieceException, PlayerException {
        List<CommandCapture> obligatoryCaptureList = new ArrayList<>();
        List<SingleCapture> singleCaptureList = new ArrayList<>();
        List<SingleCapture> newSingleCaptureList = new ArrayList<>();
        if(game.getCurrentTurn() == game.getPlayer1()) {
            for(Piece piece: game.getBoard().getWhitePieces()) {
                recursiveUpdateSingleCaptureList(game.getBoard(), newSingleCaptureList, piece);
                compareTwoLists(singleCaptureList, newSingleCaptureList);
                newSingleCaptureList.clear();
            }
        } else if(game.getCurrentTurn() == game.getPlayer2()) {
            for(Piece piece: game.getBoard().getBlackPieces()) {
                recursiveUpdateSingleCaptureList(game.getBoard(), newSingleCaptureList, piece);
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

    private static void compareTwoLists(List<SingleCapture> singleCaptureList,
                                       List<SingleCapture> newSingleCaptureList)
            throws BoardException, SquareException {
        if(newSingleCaptureList.size() > singleCaptureList.size()) {
            //first check number of captured piece
            singleCaptureList.clear();
            singleCaptureList.addAll(newSingleCaptureList);
        } else if ((newSingleCaptureList.size() == singleCaptureList.size()) &&
                (newSingleCaptureList.size() != 0)) {
            if((!singleCaptureList.get(0).pieceOnFromCoordinatesIsKing()) &&
                    (newSingleCaptureList.get(0).pieceOnFromCoordinatesIsKing())) {
                //second check piece is a king
                singleCaptureList.clear();
                singleCaptureList.addAll(newSingleCaptureList);
            } else if((singleCaptureList.get(0).pieceOnFromCoordinatesIsKing()) &&
                    (newSingleCaptureList.get(0).pieceOnFromCoordinatesIsKing())) {
                int numberOfKingInSingleCaptureList = 0;
                for(SingleCapture singleCapture : singleCaptureList) {
                    if(singleCapture.pieceOnCaptureCoordinatesIsKing()) {
                        numberOfKingInSingleCaptureList++;
                    }
                }
                int numberOfKingInNewSingleCaptureList = 0;
                for(SingleCapture singleCapture : newSingleCaptureList) {
                    if(singleCapture.pieceOnCaptureCoordinatesIsKing()) {
                        numberOfKingInNewSingleCaptureList++;
                    }
                }
                System.out.println(numberOfKingInSingleCaptureList);
                System.out.println(numberOfKingInNewSingleCaptureList);
                if(numberOfKingInNewSingleCaptureList > numberOfKingInSingleCaptureList) {
                    //third check number of captured king
                    singleCaptureList.clear();
                    singleCaptureList.addAll(newSingleCaptureList);
                } else if((numberOfKingInNewSingleCaptureList == numberOfKingInSingleCaptureList)
                        && (numberOfKingInNewSingleCaptureList > 0)) {
                    for(int j = 0; j < singleCaptureList.size(); j++) {
                        if((newSingleCaptureList.get(j).pieceOnToCoordinatesIsKing())
                                && (!singleCaptureList.get(j).pieceOnToCoordinatesIsKing())) {
                            //last check closer king
                            singleCaptureList.clear();
                            singleCaptureList.addAll(newSingleCaptureList);
                        }
                    }
                }
            }
        }
    }

    private static void recursiveUpdateSingleCaptureList(Board board, List<SingleCapture> singleCaptureList,
                                                         Piece piece)
            throws SquareContentException, CoordinatesException, PieceColorException,
            SquareException, BoardException, PieceException {
        if(piece != null) {
            List<Square> reachableSquaresList = BoardUtils.getAllReachableSquares(board, piece.getSquare());
            for(Square square: reachableSquaresList) {
                SingleCapture singleCapture = new SingleCapture(board,
                        piece.getSquare().getSquareCoordinates(),
                        square.getSquareCoordinates());
                List<SingleCapture> newSingleCaptureList = new ArrayList<>(singleCaptureList);
                if (singleCapture.isValid()) {
                    newSingleCaptureList.add(singleCapture);
                    singleCapture.run();
                    compareTwoLists(singleCaptureList, newSingleCaptureList);
                    recursiveUpdateSingleCaptureList(board, singleCaptureList,
                            BoardUtils.researchPiece(board,
                                    board.getSquare(singleCapture.getToCoordinates())));
                    singleCapture.runBack();
                }
            }
        }
    }

}

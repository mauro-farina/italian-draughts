package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.exception.*;

import java.util.List;
import java.util.ArrayList;

public class ObligatoryCapture {

    private static void compareTwoLists(List<SingleCapture> singleCaptureList,
                                       List<SingleCapture> newSingleCaptureList)
            throws BoardException, SquareException {
        if(newSingleCaptureList.size() > singleCaptureList.size()) {
            //first check number of captured piece
            singleCaptureList.clear();
            singleCaptureList.addAll(newSingleCaptureList);
        } else if (newSingleCaptureList.size() == singleCaptureList.size()) {
            if((!singleCaptureList.get(0).pieceOnFromCoordinatesIsKing()) &&
                    (newSingleCaptureList.get(0).pieceOnFromCoordinatesIsKing())) {
                //second check piece is a king
                singleCaptureList.clear();
                singleCaptureList.addAll(newSingleCaptureList);
            } else if((!singleCaptureList.get(0).pieceOnFromCoordinatesIsKing()) &&
                    (!newSingleCaptureList.get(0).pieceOnFromCoordinatesIsKing())) {
                int numberOfKingInSingleCaptureList = 0;
                for(SingleCapture singleCapture : singleCaptureList) {
                    if(singleCapture.pieceOnToCoordinatesIsKing()) {
                        numberOfKingInSingleCaptureList++;
                    }
                }
                int numberOfKingInNewSingleCaptureList = 0;
                for(SingleCapture singleCapture : newSingleCaptureList) {
                    if(singleCapture.pieceOnToCoordinatesIsKing()) {
                        numberOfKingInNewSingleCaptureList++;
                    }
                }
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

    public static List<CommandCapture> getObligatoryCapture(Board mainBoard, Piece movedPiece)
            throws BoardException, SquareContentException, CoordinatesException,
            PieceColorException, SquareException, PieceException {
        List<CommandCapture> obligatoryCaptureList = new ArrayList<>();
        List<SingleCapture> singleCaptureList = new ArrayList<>();
        updateSingleCaptureList(mainBoard, singleCaptureList, movedPiece);
        for(SingleCapture singleCapture : singleCaptureList) {
            obligatoryCaptureList.add(
                    new CommandCapture(singleCapture.getFromCoordinates(),
                            singleCapture.getPieceToCaptureCoordinates()));
        }
        return obligatoryCaptureList;
    }

    private static void updateSingleCaptureList(Board mainBoard, List<SingleCapture> singleCaptureList, Piece movedPiece)
            throws SquareContentException, CoordinatesException, PieceColorException,
            SquareException, BoardException, PieceException {
        if(movedPiece != null) {
            for (int i = 0; i < mainBoard.getReachableSquares(movedPiece).size(); i++) {
                List<SingleCapture> newSingleCaptureList = new ArrayList<>();
                SingleCapture singleCapture = new SingleCapture(mainBoard,
                        mainBoard.getReachableSquares(movedPiece).get(i).getSquareCoordinates(),
                        movedPiece.getSquare().getSquareCoordinates());
                if (singleCapture.isValid()) {
                    newSingleCaptureList.add(singleCapture);
                    singleCapture.run();
                    recursiveUpdateSingleCaptureList(mainBoard, singleCaptureList,
                                BoardUtils.researchPiece(mainBoard,
                                        mainBoard.getSquare(singleCapture.getToCoordinates())));
                    compareTwoLists(singleCaptureList, newSingleCaptureList);
                    singleCapture.runBack();
                }
            }
        }
    }

    private static void recursiveUpdateSingleCaptureList(Board mainBoard, List<SingleCapture> singleCaptureList,
                                                         Piece piece)
            throws SquareContentException, CoordinatesException, PieceColorException,
            SquareException, BoardException, PieceException {
        if(piece != null) {
            for (int i = 0; i < mainBoard.getReachableSquares(piece).size(); i++) {
                List<SingleCapture> newSingleCaptureList = new ArrayList<>();
                SingleCapture singleCapture = new SingleCapture(mainBoard,
                        piece.getSquare().getSquareCoordinates(),
                        mainBoard.getReachableSquares(piece).get(i).getSquareCoordinates());
                if (singleCapture.isValid()) {
                    newSingleCaptureList.add(singleCapture);
                    singleCapture.run();
                    recursiveUpdateSingleCaptureList(mainBoard, singleCaptureList,
                            BoardUtils.researchPiece(mainBoard,
                                    mainBoard.getSquare(singleCapture.getToCoordinates())));
                    compareTwoLists(singleCaptureList, newSingleCaptureList);
                    singleCapture.runBack();
                }
            }
        }
    }

}

package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.BoardUtils;
import it.units.inginf.italiandraughts.board.Board;
import it.units.inginf.italiandraughts.board.Piece;
import it.units.inginf.italiandraughts.board.Square;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.exception.BoardException;
import it.units.inginf.italiandraughts.exception.CommandException;
import it.units.inginf.italiandraughts.exception.CoordinatesException;
import it.units.inginf.italiandraughts.exception.SquareException;

import java.util.List;

public class CommandTo extends Command {

    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates toCoordinates;

    public CommandTo(SquareCoordinates fromCoordinates, SquareCoordinates toCoordinates) {
        super(CommandType.TO);
        this.fromCoordinates = fromCoordinates;
        this.toCoordinates = toCoordinates;
    }

    public SquareCoordinates getFromCoordinates() {
        return fromCoordinates;
    }

    public SquareCoordinates getToCoordinates() {
        return toCoordinates;
    }

    public boolean isValid(Board board) throws SquareException, BoardException, CommandException, CoordinatesException {
        if (board == null) {
            throw new BoardException("CommandTo.isValid() does not accept this board because it is null");
        }
        Square startingSquare = board.getSquare(fromCoordinates);
        Square arrivalSquare = board.getSquare(toCoordinates);
        Piece selectedPiece = BoardUtils.researchPiece(board, startingSquare);
        if (selectedPiece == null) {
            throw new CommandException("CommandTo.isValid(): there is no piece located on " + startingSquare.getSquareName().toString());
        }
        if (!arrivalSquare.isFree()) {
            throw new SquareException("CommandTo.isValid(): there already is a piece on " + arrivalSquare.getSquareName().toString());
        }
        List<Square> listReachableSquares = board.getReachableSquares(selectedPiece);
        if (listReachableSquares.contains(arrivalSquare)) {
            return true;
        } else {
            throw new CommandException("CommandTo.isValid(): cannot reach " + arrivalSquare.getSquareName().toString()
                    + " from " + startingSquare.getSquareName().toString());
        }
    }

}

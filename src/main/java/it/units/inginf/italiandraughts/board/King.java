package it.units.inginf.italiandraughts.board;

import it.units.inginf.italiandraughts.exception.PieceColorException;
import it.units.inginf.italiandraughts.exception.SquareException;

public class King extends Man {

    public King(PieceColor color, Square square) throws PieceColorException, SquareException {
        super(color, square);
    }

    @Override
    public boolean isMan() {
        return false;
    }

    @Override
    public boolean isKing() {
        return true;
    }
}

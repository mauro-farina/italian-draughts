package it.units.inginf.italiandraughts.board;

public enum SquareContent {

    WHITE_MAN("[w]"), WHITE_KING("[W]"),
    BLACK_MAN("[b]"), BLACK_KING("[B]"),
    EMPTY("[ ]");

    private final String symbol;

    SquareContent(String symbol){
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

}

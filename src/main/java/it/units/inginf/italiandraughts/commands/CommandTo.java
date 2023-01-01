package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.board.SquareCoordinates;

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
}

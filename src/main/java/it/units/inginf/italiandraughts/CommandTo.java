package it.units.inginf.italiandraughts;

public class CommandTo extends Command {

    private final int[] fromCoordinates;
    private final int[] toCoordinates;

    public CommandTo(int[] fromCoordinates, int[] toCoordinates) {
        super(CommandType.TO);
        this.fromCoordinates = fromCoordinates;
        this.toCoordinates = toCoordinates;
    }

    public int[] getFromCoordinates() {
        return fromCoordinates;
    }

    public int[] getToCoordinates() {
        return toCoordinates;
    }
}

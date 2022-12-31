package it.units.inginf.italiandraughts.commands;

public class CommandCapture extends Command {

    private final int[] fromCoordinates;
    private final int[] pieceToCaptureCoordinates;

    public CommandCapture(int[] fromCoordinates, int[] pieceToCaptureCoordinates) {
        super(CommandType.CAPTURE);
        this.fromCoordinates = fromCoordinates;
        this.pieceToCaptureCoordinates = pieceToCaptureCoordinates;
    }

    public int[] getFromCoordinates() {
        return fromCoordinates;
    }

    public int[] getPieceToCaptureCoordinates() {
        return pieceToCaptureCoordinates;
    }
}

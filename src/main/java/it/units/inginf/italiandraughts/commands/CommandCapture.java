package it.units.inginf.italiandraughts.commands;

import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.exception.CoordinatesException;

public class CommandCapture extends Command {

    private final SquareCoordinates fromCoordinates;
    private final SquareCoordinates pieceToCaptureCoordinates;
    private final SquareCoordinates toCoordinates;

    public CommandCapture(SquareCoordinates fromCoordinates, SquareCoordinates pieceToCaptureCoordinates) throws CoordinatesException {
        super(CommandType.CAPTURE);
        this.fromCoordinates = fromCoordinates;
        this.pieceToCaptureCoordinates = pieceToCaptureCoordinates;
        // calculate toCoordinates
        // given fromSquareCoordinates and pieceToCaptureSquareCoordinates, find toSquareCoordinates
        int toCoordinateY, toCoordinateX;
        if(fromCoordinates.getCoordinateY() > pieceToCaptureCoordinates.getCoordinateY()){ // *6 capture *5 (ex: C6 capture B5)
            toCoordinateY = pieceToCaptureCoordinates.getCoordinateY() - 1; // moves to *4
        } else { // *3 capture *4 (ex: B3 capture C4)
            toCoordinateY = pieceToCaptureCoordinates.getCoordinateY() + 1; // moves to *5
        }
        if(fromCoordinates.getCoordinateX() > pieceToCaptureCoordinates.getCoordinateX()) { // ex: D* capture C* (ex: D3 capture C4)
            toCoordinateX = pieceToCaptureCoordinates.getCoordinateX() - 1; // moves to B*
        } else { // B* capture C* (ex: B3 capture C4)
            toCoordinateX = pieceToCaptureCoordinates.getCoordinateX() + 1; // moves to D*
        }
        try {
            this.toCoordinates = new SquareCoordinates(toCoordinateX, toCoordinateY);
        } catch (Exception exception) {
            throw new CoordinatesException("CommandCapture.CommandCapture() -> cannot capture piece on the edge of the board");
        }
    }

    public SquareCoordinates getFromCoordinates() {
        return fromCoordinates;
    }

    public SquareCoordinates getPieceToCaptureCoordinates() {
        return pieceToCaptureCoordinates;
    }

    public SquareCoordinates getToCoordinates() {
        return toCoordinates;
    }
}

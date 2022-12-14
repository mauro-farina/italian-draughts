package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareName;
import it.units.inginf.italiandraughts.exception.CoordinatesException;

public class Utils {

    public static int[] convertToCoordinates(String coordinatesString) throws CoordinatesException {
        int[] coordinates = new int[2];
        if(coordinatesString.length() != 2) {
            throw new CoordinatesException("Utils.convertToCoordinates() does not accept this coordinatesString");
        } else {
            switch(coordinatesString.charAt(0)) {
                case 'A' -> coordinates[0] = 0;
                case 'B' -> coordinates[0] = 1;
                case 'C' -> coordinates[0] = 2;
                case 'D' -> coordinates[0] = 3;
                case 'E' -> coordinates[0] = 4;
                case 'F' -> coordinates[0] = 5;
                case 'G' -> coordinates[0] = 6;
                case 'H' -> coordinates[0] = 7;
                default -> throw new CoordinatesException("Utils.convertToCoordinates() does not accept this coordinatesString");
            }
            switch(coordinatesString.charAt(1)) {
                case '1' -> coordinates[1] = 0;
                case '2' -> coordinates[1] = 1;
                case '3' -> coordinates[1] = 2;
                case '4' -> coordinates[1] = 3;
                case '5' -> coordinates[1] = 4;
                case '6' -> coordinates[1] = 5;
                case '7' -> coordinates[1] = 6;
                case '8' -> coordinates[1] = 7;
                default -> throw new CoordinatesException("Utils.convertToCoordinates() does not accept this coordinatesString");
            }
            return coordinates;
        }
    }

    public static int[] convertToCoordinates(SquareName squareName) throws CoordinatesException {
        return convertToCoordinates(squareName.toString());
    }
}

package it.units.inginf.italiandraughts;

public class Command {
    private int[] coordinatesStartingSquare = new int[2];

    private final CommandType commandType;

    private int[] coordinatesArrivalSquare = new int[2];

    public Command(String input) throws Exception {
        if(input == null) {
            throw new Exception("Input cannot be null");
        } else {
            String[] inputSplit = input.split(" ");
            if (inputSplit.length == 1) {
                if(inputSplit[0].equalsIgnoreCase("HELP")) {
                    commandType = CommandType.HELP;
                } else if((inputSplit[0].equalsIgnoreCase("SUR")) ||
                        (inputSplit[0].equalsIgnoreCase("SURRENDER"))) {
                    commandType = CommandType.SURRENDER;
                } else {
                    throw new Exception("Invalid command");
                }
            } else if (inputSplit.length == 3) {
                coordinatesStartingSquare = convertToCoordinates(inputSplit[0]);
                coordinatesArrivalSquare = convertToCoordinates(inputSplit[2]);
                if(inputSplit[1].equalsIgnoreCase("TO")) {
                    commandType = CommandType.TO;
                } else if((inputSplit[1].equalsIgnoreCase("CAP")) ||
                        (inputSplit[1].equalsIgnoreCase("CAPT")) ||
                        (inputSplit[1].equalsIgnoreCase("CAPTURE"))) {
                    commandType = CommandType.CAPTURE;
                } else {
                    throw new Exception("Invalid command");
                }
            } else {
                throw new Exception("Invalid command");
            }
        }
    }

    private int[] convertToCoordinates(String coordinatesString) throws Exception {
        int[] coordinates = new int[2];
        if(coordinatesString.length() != 2) {
            throw new Exception("Invalid coordinates");
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
                default -> throw new Exception("Invalid coordinates");
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
                default -> throw new Exception("Invalid coordinates");
            }
            return coordinates;
        }
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public int[] getCoordinatesStartingSquare() {
        return this.coordinatesStartingSquare;
    }

    public int[] getCoordinatesArrivalSquare() {
        return this.coordinatesArrivalSquare;
    }

}

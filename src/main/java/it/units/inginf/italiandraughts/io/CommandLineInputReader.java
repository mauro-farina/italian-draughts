package it.units.inginf.italiandraughts.io;

import java.util.Scanner;

public class CommandLineInputReader implements InputReader {

    private final Scanner scanner;

    public CommandLineInputReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}

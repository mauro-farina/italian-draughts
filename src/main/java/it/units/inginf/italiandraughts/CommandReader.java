package it.units.inginf.italiandraughts;

import java.util.Scanner;

public class CommandReader implements CLIReader {

    private Scanner scanner;

    public CommandReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}

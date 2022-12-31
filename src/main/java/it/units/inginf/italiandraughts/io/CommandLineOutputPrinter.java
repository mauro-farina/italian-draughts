package it.units.inginf.italiandraughts.io;

import java.io.PrintStream;

public class CommandLineOutputPrinter implements OutputPrinter {

    private final PrintStream printStream;

    public CommandLineOutputPrinter() {
        this.printStream = System.out;
    }

    @Override
    public void print(String stringToPrint) {
        printStream.println(stringToPrint);
    }
}

package it.units.inginf.italiandraughts.io;

import java.io.PrintStream;

public class CommandLineOutputPrinter implements OutputPrinter {

    private final PrintStream printStreamForMessage;
    private final PrintStream printStreamForError;

    public CommandLineOutputPrinter() {
        this.printStreamForMessage = System.out;
        this.printStreamForError = System.err;
    }

    @Override
    public void print(String stringToPrint) {
        printStreamForMessage.println(stringToPrint);
    }

    @Override
    public void print(String stringToPrint, boolean error) {
        if(error) {
            printStreamForError.println(stringToPrint);
        } else {
            printStreamForMessage.println(stringToPrint);
        }
    }
}

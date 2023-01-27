package it.units.inginf.italiandraughts.io;

public interface OutputPrinter {
    void print(String stringToPrint);
    default void print(String stringToPrint, boolean error) {
        // default body (empty) -> implementation is optional
    }
}

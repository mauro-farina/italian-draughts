package it.units.inginf.italiandraughts.game;

import it.units.inginf.italiandraughts.commands.CommandCapture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandCaptureList extends ArrayList<CommandCapture> {


    public CommandCaptureList(List<CommandCapture> capturesList) {
        super(capturesList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<CommandCapture> iterator = iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
            if (iterator.hasNext()) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

}

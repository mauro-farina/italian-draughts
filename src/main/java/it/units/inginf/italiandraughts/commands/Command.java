package it.units.inginf.italiandraughts.commands;

public abstract class Command {

    private final CommandType commandType;

    public Command(CommandType commandType){
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

}

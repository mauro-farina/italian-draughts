# italian-draughts

Italian Draughts game, made by [Luca Filippi](https://github.com/Luca-Filippi) and [Mauro Farina](https://github.com/mauro-farina) as a project for the course Software Development Methods.

The rules and the description of the game are visible on this [link](https://en.m.wikipedia.org/wiki/Italian_draughts).

The src folder contains the java code of the game and related tests.

The [main](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts) folder has the following elements:

-> the [Main.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/Main.java) class, the only executable class. By executing its code it is possible to play our Italian Draughts;

-> the [Utils.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/Utils.java) class, a class with 2 utility methods related to the conversion of coordinates from String to int[] or from SquareName to int[];

-> the [BoardUtils.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/BoardUtils.java) class, a class with 3 utility methods that change the board.
      
-> the [CommandParser.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/CommandParser.java) class, given a string representing a command, this class returns the type of associated command;

-> the packege [it.units.inginf.italiandraughts.board](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/board) contains all the classes that represent the board and its components (Piece, Square, ...);

-> the packege [it.units.inginf.italiandraughts.commands](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/commands) contains all the classes that model the various types of commands and the class to execute them (CommandRunner);

-> the packege [it.units.inginf.italiandraughts.exception](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/exception) contains all the classes that model the various types of exceptions that can occur in all other classes;

-> the packege [it.units.inginf.italiandraughts.game](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/game);

-> the packege [it.units.inginf.italiandraughts.io](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/io) deals with managing the input and output systems that will be used in the Main and in the Game;


The [test](https://github.com/mauro-farina/italian-draughts/tree/main/src/test/java/it/units/inginf/italiandraughts) folder contains many test classes associated with individual classes contained in the main folder.

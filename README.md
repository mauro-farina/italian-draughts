# italian-draughts

Italian Draughts game, made by [Luca Filippi](https://github.com/Luca-Filippi) and [Mauro Farina](https://github.com/mauro-farina) as a project for the course Software Development Methods.

The rules and the description of the game are visible on this [link](https://en.m.wikipedia.org/wiki/Italian_draughts).

The src folder contains the java code of the game and related tests.

The [main](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts) folder has the following elements:

-> the [Main.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/Main.java) class, the only executable class. By executing its code it is possible to play our Italian Draughts;

-> the [Utils.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/Utils.java) class, a class with 2 utility methods related to the conversion of coordinates from String to int[] or from SquareName to int[];

-> the [BoardUtils.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/BoardUtils.java) class, a class with 3 utility methods that update the board:

      -> removePiece(...) removes a piece from the board;
      
      -> toCrown(...) creates a king on the square of a man reached its last line and deletes this man;
      
      -> researchPiece(...) given a square, this method finds (if it exists) the piece located on this square;
      
-> the [CommandParser.java](https://github.com/mauro-farina/italian-draughts/blob/main/src/main/java/it/units/inginf/italiandraughts/CommandParser.java) class, given a string representing a command, this class returns the type of associated command;

-> the packege [it.units.inginf.italiandraughts.board](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/board);

-> the packege [it.units.inginf.italiandraughts.commands](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/commands);

-> the packege [it.units.inginf.italiandraughts.exception](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/exception);

-> the packege [it.units.inginf.italiandraughts.game](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/game);

-> the packege [it.units.inginf.italiandraughts.io](https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts/io);


The [test](https://github.com/mauro-farina/italian-draughts/tree/main/src/test/java/it/units/inginf/italiandraughts) folder contains many test classes associated with individual classes contained in the main folder.

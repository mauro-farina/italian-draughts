# italian-draughts

Italian Draughts game, made by [Luca Filippi](https://github.com/Luca-Filippi) and [Mauro Farina](https://github.com/mauro-farina) as a project for the course Software Development Methods.

The src folder contains the java code of the game ([main] (https://github.com/mauro-farina/italian-draughts/tree/main/src/main/java/it/units/inginf/italiandraughts) and related tests ([test](https://github.com/mauro-farina/italian-draughts/tree/main/src/test/java/it/units/inginf/italiandraughts)).
The main folder has the following elements:
-> the Main.java class, the only executable class. By executing its code it is possible to play our Italian Draughts;
-> the Utils.java class, a class with 2 utility methods related to the conversion of coordinates from String to int[] or from SquareName to int[];
-> the BoardUtils.java class, a class with 3 utility methods that update the board:
      -> removePiece(...) removes a piece from the board;
      -> toCrown(...) creates a king on the square of a man reached its last line and deletes this man;
      -> researchPiece(...) given a square, this method finds (if it exists) the piece located on this square;
-> the CommandParser.java class, given a string representing a command, this class returns the type of associated command;
-> the it.units.inginf.italiandraughts.board package;
-> the it.units.inginf.italiandraughts.commands package;
-> the it.units.inginf.italiandraughts.exception package;
-> the it.units.inginf.italiandraughts.game package;
-> the it.units.inginf.italiandraughts.io package;

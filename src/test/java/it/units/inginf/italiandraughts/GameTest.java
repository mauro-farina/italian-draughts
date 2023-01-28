package it.units.inginf.italiandraughts;

import it.units.inginf.italiandraughts.board.SquareContent;
import it.units.inginf.italiandraughts.board.SquareCoordinates;
import it.units.inginf.italiandraughts.board.Man;
import it.units.inginf.italiandraughts.board.PieceColor;
import it.units.inginf.italiandraughts.commands.CommandCapture;
import it.units.inginf.italiandraughts.game.Game;
import it.units.inginf.italiandraughts.game.GameState;
import it.units.inginf.italiandraughts.game.Player;
import it.units.inginf.italiandraughts.game.PlayerColor;
import it.units.inginf.italiandraughts.io.CommandLineInputReader;
import it.units.inginf.italiandraughts.io.CommandLineOutputPrinter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GameTest {

    @Test
    void simpleMandatoryCapturesTest() {
        String[] input = {
                "b3 to c4",
                "g6 to h5",
                "c4 to b5",
                "a6 cap b5",
                "d3 cap c4",
                "c6 cap b5",
                "surrender" // White surrenders -> Black wins
        };

        final int[] i = {-1};
        List<String> output = new ArrayList<>();
        try {
            Game game = new Game(
                    new Player("White", PlayerColor.WHITE),
                    new Player("Black", PlayerColor.BLACK),
                    () -> {
                        i[0]+=1;
                        return input[i[0]];
                    },
                    outputMsg -> {
                        if(outputMsg.contains("CAPTURE") || outputMsg.contains("winner")) {
                            output.add(outputMsg);
                        }
                    }
            );

            List<String> expectedOutput = new ArrayList<>();
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(0, 5),
                    new SquareCoordinates(1, 4)).toString()); //A6 CAPTURE B5
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(2, 5),
                    new SquareCoordinates(1, 4)).toString()); //C6 CAPTURE B5
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(3, 2),
                    new SquareCoordinates(2, 3)).toString()); //D3 CAPTURE C4

            expectedOutput.add("The winner is Black"); //must be adjusted if winner message changes in Game.start()

            game.start();

            for(String expectedOutputMessage : expectedOutput) {
                assertTrue(output.contains(expectedOutputMessage));
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void doubleMandatoryCaptureTest() {
        String[] input = {
                "b3 to c4",
                "a6 to b5",
                "c4 cap b5",
                "c6 to d5",
                "c2 to b3",
                "b7 to c6",
                "d1 to c2",
                "d5 to e4",
                "f3 cap e4",
                "d5 cap c6",
                "a8 cap b7",
                "surrender" // White surrenders -> Black wins
        };

        final int[] i = {-1};
        List<String> output = new ArrayList<>();
        try {
            Game game = new Game(
                    new Player("White", PlayerColor.WHITE),
                    new Player("Black", PlayerColor.BLACK),
                    () -> {
                        i[0]+=1;
                        return input[i[0]];
                    },
                    outputMsg -> {
                        if(outputMsg.contains("CAPTURE") || outputMsg.contains("winner")) {
                            output.add(outputMsg);
                        }
                    }
            );

            List<String> expectedOutput = new ArrayList<>();
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(2, 3),
                    new SquareCoordinates(1, 4)).toString()); //C4 CAPTURE B5
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(5, 2),
                    new SquareCoordinates(4, 3)).toString()); //F3 CAPTURE E4
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(3, 4),
                    new SquareCoordinates(2, 5)).toString()); //D5 CAPTURE C6
            expectedOutput.add(new CommandCapture(
                    new SquareCoordinates(0, 7),
                    new SquareCoordinates(1, 6)).toString()); //A8 CAPTURE B7
            expectedOutput.add("The winner is Black"); //must be adjusted if winner message changes in Game.start()

            game.start();

            for(String expectedOutputMessage : expectedOutput) {
                assertTrue(output.contains(expectedOutputMessage));
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void checkTurnAlternation() {
        String[] input = {
                "a3 to b4", //w
                "b3 to c4", //w
                "a6 to b5", //b
                "surrender" // w -> Black wins
        };

        final int[] i = {-1};
        List<String> output = new ArrayList<>();
        try {
            Game game = new Game(
                    new Player("White", PlayerColor.WHITE),
                    new Player("Black", PlayerColor.BLACK),
                    () -> {
                        i[0]+=1;
                        return input[i[0]];
                    },
                    outputMsg -> {
                        if(outputMsg.contains("winner")) {
                            output.add(outputMsg);
                        }
                    }
            );

            game.start();

            assertTrue(output.get(0).contains("Black"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void checkGameStateAfterSurrender() {
        try {
            Game game = new Game(
                    new Player("White", PlayerColor.WHITE),
                    new Player("Black", PlayerColor.BLACK),
                    () -> "surrender",
                    outputMsg -> { }
            );

            game.start();

            assertEquals(GameState.OVER, game.getGameState());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void checkPlayer1() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
            new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getCurrentTurn(), game.getPlayer1());
    }

    @Test
    void checkPlayer2() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.changeTurn();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getCurrentTurn(), game.getPlayer2());
    }

    @Test
    void victoryConditionIsTrue() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            for(int i = 11; i >= 0; i--) {
                BoardUtils.removePiece(game.getBoard(), game.getBoard().getWhitePieces().get(i));
            }
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertTrue(game.checkVictoryCondition());
    }

    @Test
    void victoryConditionIsFalse() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertFalse(game.checkVictoryCondition());
    }

    @Test
    void checkWinnerPlayer() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.setWinnerPlayer(game.getPlayer1());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(game.getWinnerPlayer(), game.getPlayer1());
    }

    @Test
    void checkDrawConditionIsTrue() {
        Game game;
        try {
            game = new Game(new Player("Luca", PlayerColor.WHITE),
                    new Player("Mauro", PlayerColor.BLACK),
                    new CommandLineInputReader(), new CommandLineOutputPrinter());
            game.initGame();
            game.getBoard().getWhitePieces().clear();
            game.getBoard().getBlackPieces().clear();
            for(short i = 0; i < 8; i++) {
                for(short j = 0; j < 8; j++) {
                    game.getBoard().getSquare(new SquareCoordinates(i, j))
                            .setSquareContent(SquareContent.EMPTY);
                }
            }
            game.getBoard().getSquare(new SquareCoordinates(1, 0))
                    .setSquareContent(SquareContent.WHITE_MAN);
            game.getBoard().getWhitePieces().add(new Man(PieceColor.WHITE,
                    game.getBoard().getSquare(new SquareCoordinates(1, 0))));
            game.getBoard().getSquare(new SquareCoordinates(0, 1))
                    .setSquareContent(SquareContent.BLACK_MAN);
            game.getBoard().getBlackPieces().add(new Man(PieceColor.BLACK,
                    game.getBoard().getSquare(new SquareCoordinates(0, 1))));
            assertTrue(game.checkDrawCondition());
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

}

package com.adaptionsoft.games.trivia;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.commons.annotation.Testable;

import com.adaptionsoft.games.uglytrivia.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameTest {
    private Game game;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeAll
    public void setUp() {
        game = new Game();
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Testable
    public void testAddingPlayers() {
        game.add("Player1");
        game.add("Player2");
        assertEquals(2, game.howManyPlayers());
    }

    @Testable
    public void testGameIsPlayableWhenAtLeastTwoPlayersAdded() {
        game.add("Player1");
        assertFalse(game.isPlayable());

        game.add("Player2");
        assertTrue(game.isPlayable());
    }

    @Testable
    public void testRollingDice() {
        game.add("Player1");
        game.roll(3);
        // El jugador debe moverse a la posición 3
        String output = outputStream.toString();
        assertTrue(output.contains("Player1's new location is 3"));
    }

    @Testable
    public void testPenaltyBox() {
        game.add("Player1");
        game.wrongAnswer(); // Envía al jugador a la caja de penalización

        // Reiniciamos la salida para la siguiente verificación
        outputStream.reset();

        game.roll(2); // Número par, no sale de la caja
        String output = outputStream.toString();
        assertTrue(output.contains("Player1 is not getting out of the penalty box"));

        outputStream.reset();

        game.roll(3); // Número impar, sale de la caja
        output = outputStream.toString();
        assertTrue(output.contains("Player1 is getting out of the penalty box"));
    }

    @Testable
    public void testWinningGame() {
        game.add("Player1");

        // Simulamos 6 respuestas correctas (ganando monedas)
        for (int i = 0; i < 6; i++) {
            game.roll(1);
            boolean stillPlaying = game.wasCorrectlyAnswered();
            if (i < 5) {
                assertTrue(stillPlaying); // Aún no ha ganado
            } else {
                assertFalse(stillPlaying); // Ha ganado en la sexta respuesta correcta
            }
        }
    }

    @Testable
    public void testAskQuestionByCategory() {
        game.add("Player1");

        // Testeamos que se pregunte por cada categoría
        int[] posicionesCategoria = { 0, 1, 2, 3 }; // Pop, Science, Sports, Rock
        String[] categorias = { "Pop", "Science", "Sports", "Rock" };

        for (int i = 0; i < posicionesCategoria.length; i++) {
            outputStream.reset();
            game.roll(posicionesCategoria[i] - game.getPlaces()[0]); // Movemos al jugador a la posición específica
            String output = outputStream.toString();
            assertTrue(output.contains("The category is " + categorias[i]));
        }
    }

    @Testable
    public void testPlayerRotation() {
        game.add("Player1");
        game.add("Player2");

        assertEquals(0, game.getCurrentPlayer());
        game.roll(1);
        game.wasCorrectlyAnswered();
        assertEquals(1, game.getCurrentPlayer()); // Debería haber cambiado al jugador 2

        game.roll(1);
        game.wasCorrectlyAnswered();
        assertEquals(0, game.getCurrentPlayer()); // Debería haber vuelto al jugador 1
    }
}
package com.adaptionsoft.games.trivia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() {
        game = new Game();
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testAddingPlayers() {
        game.add("Player1");
        game.add("Player2");
        assertEquals(2, game.howManyPlayers());
    }

    @Test
    public void testGameIsPlayableWhenAtLeastTwoPlayersAdded() {
        game.add("Player1");
        assertFalse(game.isPlayable());

        game.add("Player2");
        assertTrue(game.isPlayable());
    }

    @Test
    public void testRollingDice() {
        game.add("Player1");
        game.roll(3);
        // El jugador debe moverse a la posición 3
        String output = outputStream.toString();
        assertTrue(output.contains("Player1's new location is 3"));
    }

    @Test
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

    @Test
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

    @Test
    public void testAskQuestionByCategory() {
        game.add("Player1");

        // Test que se pregunte por cada categoría según la posición del jugador
        int[] rolls = { 0, 1, 2, 3 }; // Movimientos para Pop, Science, Sports, Rock
        String[] categorias = { "Pop", "Science", "Sports", "Rock" };

        for (int i = 0; i < rolls.length; i++) {
            outputStream.reset();
            game.roll(rolls[i] + 1); // roll tiene que ser al menos 1
            String output = outputStream.toString();
            assertTrue(output.contains("The category is " + categorias[i]), "Debería contener categoría " + categorias[i]);
        }
    }

    @Test
    public void testPlayerRotation() {
        game.add("Player1");
        game.add("Player2");

        game.roll(1);
        game.wasCorrectlyAnswered();

        outputStream.reset();
        game.roll(1);
        String output = outputStream.toString();
        assertTrue(output.contains("Player2 is the current player"));

        game.wasCorrectlyAnswered();

        outputStream.reset();
        game.roll(1);
        output = outputStream.toString();
        assertTrue(output.contains("Player1 is the current player"));
    }
}

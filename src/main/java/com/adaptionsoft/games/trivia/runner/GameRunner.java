package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {
    public static void main(String[] args) {
        playGame(new Game(), new Random());
    }
    
    public static void playGame(Game game, Random randomGenerator) {
        // Agregar jugadores
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");
        
        boolean notAWinner;
        
        do {
            // Lanzar el dado (1-6)
            int roll = randomGenerator.nextInt(5) + 1;
            game.roll(roll);
            
            // 1/9 de probabilidad de respuesta incorrecta
            if (randomGenerator.nextInt(9) == 7) {
                notAWinner = game.wrongAnswer();
            } else {
                notAWinner = game.wasCorrectlyAnswered();
            }
        } while (notAWinner);
    }
}
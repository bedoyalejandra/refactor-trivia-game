package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    // Constantes para eliminar números mágicos
    private static final int WINNING_SCORE = 6;
    private static final int BOARD_SIZE = 12;
    private static final int QUESTIONS_COUNT = 50;
    
    // Colecciones
    private ArrayList<Player> players = new ArrayList<Player>();
    private LinkedList<String> popQuestions = new LinkedList<String>();
    private LinkedList<String> scienceQuestions = new LinkedList<String>();
    private LinkedList<String> sportsQuestions = new LinkedList<String>();
    private LinkedList<String> rockQuestions = new LinkedList<String>();
    
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public Game() {
    	initializeQuestions();
    }
    
    private void initializeQuestions() {
        for (int i = 0; i < QUESTIONS_COUNT; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }
	
    
    private String createRockQuestion(int index) {
        return "Rock Question " + index;
    }
    
    public boolean isPlayable() {
        return (players.size() >= 2);
    }

    public boolean add(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }
    
    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);
        
        if (currentPlayer.isInPenaltyBox()) {
            handlePenaltyBoxRoll(roll, currentPlayer);
        } else {
            movePlayerAndAskQuestion(roll, currentPlayer);
        }
    }
    
    private void handlePenaltyBoxRoll(int roll, Player player) {
        if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;
            System.out.println(player.getName() + " is getting out of the penalty box");
            movePlayerAndAskQuestion(roll, player);
        } else {
            System.out.println(player.getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
        }
    }
    
    private void movePlayerAndAskQuestion(int roll, Player player) {
        player.moveForward(roll, BOARD_SIZE);
        
        System.out.println(player.getName() + "'s new location is " + player.getPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        String category = currentCategory();
        if (category.equals("Pop")) {
            System.out.println(popQuestions.removeFirst());
        } else if (category.equals("Science")) {
            System.out.println(scienceQuestions.removeFirst());
        } else if (category.equals("Sports")) {
            System.out.println(sportsQuestions.removeFirst());
        } else if (category.equals("Rock")) {
            System.out.println(rockQuestions.removeFirst());
        }
    }
    
    private String currentCategory() {
        int place = players.get(currentPlayerIndex).getPlace();
        if (place % 4 == 0) return "Pop";
        if (place % 4 == 1) return "Science";
        if (place % 4 == 2) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean winner = true;
        
        if (currentPlayer.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                winner = handleCorrectAnswer(currentPlayer);
            }
            moveToNextPlayer();
            return winner;
        } else {
            winner = handleCorrectAnswer(currentPlayer);
            moveToNextPlayer();
            return winner;
        }
    }
    
    private boolean handleCorrectAnswer(Player player) {
        System.out.println("Answer was correct!!!!");
        player.addCoin();
        System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");
        
        return !player.hasWon(WINNING_SCORE);
    }
    
    private void moveToNextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }
    
    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);
        
        moveToNextPlayer();
        return true;
    }

    public int getCurrentPlayer() {
        return currentPlayerIndex;
    }
}

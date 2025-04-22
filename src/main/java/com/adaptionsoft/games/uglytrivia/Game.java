package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
    // Constantes para eliminar números mágicos
    private static final int WINNING_SCORE = 6;
    private static final int BOARD_SIZE = 12;
    
    // Colecciones
    private ArrayList<Player> players = new ArrayList<>();
    private QuestionDeck questionDeck = new QuestionDeck();
    
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;
    
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
        Player currentPlayer = getCurrentPlayer();
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);
        
        if (currentPlayer.isInPenaltyBox()) {
            handlePenaltyBoxRoll(roll, currentPlayer);
        } else {
            movePlayerAndAskQuestion(roll, currentPlayer);
        }
    }
    
    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
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
        
        QuestionCategory currentCategory = getCurrentCategory();
        System.out.println("The category is " + currentCategory.getDisplayName());
        
        String question = questionDeck.getNextQuestion(currentCategory);
        System.out.println(question);
    }
    
    private QuestionCategory getCurrentCategory() {
        int place = getCurrentPlayer().getPlace();
        return QuestionCategory.getCategoryForPosition(place);
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = getCurrentPlayer();
        boolean gameNotOver = true;
        
        if (currentPlayer.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                gameNotOver = handleCorrectAnswer(currentPlayer);
            }
        } else {
            gameNotOver = handleCorrectAnswer(currentPlayer);
        }
        
        moveToNextPlayer();
        return gameNotOver;
    }
    
    private boolean handleCorrectAnswer(Player player) {
        System.out.println("Answer was correct!!!!");
        player.addCoin();
        System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");
        
        return !player.hasWon(WINNING_SCORE);
    }
    
    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    public boolean wrongAnswer() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);
        
        moveToNextPlayer();
        return true;
    }
}
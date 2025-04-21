package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    // Constantes para eliminar números mágicos
    private static final int MAX_PLAYERS = 6;
    private static final int WINNING_SCORE = 6;
    private static final int BOARD_SIZE = 12;
    private static final int QUESTIONS_COUNT = 50;
    
    // Colecciones tipadas
    private ArrayList<String> players = new ArrayList<String>();
    private int[] places = new int[MAX_PLAYERS];
    private int[] purses = new int[MAX_PLAYERS];
    private boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];
    
    private LinkedList<String> popQuestions = new LinkedList<String>();
    private LinkedList<String> scienceQuestions = new LinkedList<String>();
    private LinkedList<String> sportsQuestions = new LinkedList<String>();
    private LinkedList<String> rockQuestions = new LinkedList<String>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public Game(){
    	for (int i = 0; i < QUESTIONS_COUNT; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public int[] getPlaces() {
        return places;
        
    }

    public void setPlaces(int[] places) {
        this.places = places;
        
    }

    public int getCurrentPlayer() {
		return currentPlayer;
		
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
		
	}

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(playerName);
	    places[howManyPlayers() - 1] = 0;
	    purses[howManyPlayers() - 1] = 0;
	    inPenaltyBox[howManyPlayers() - 1] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerBy(roll);
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ places[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
			movePlayerBy(roll);
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}
	
	private void movePlayerBy(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] >= BOARD_SIZE) {
			places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;
		}
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				moveToNextPlayer();
				
				return winner;
			} else {
				moveToNextPlayer();
				return true;
			}
			
		} else {
			System.out.println("Answer was correct!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			moveToNextPlayer();
			
			return winner;
		}
	}
	
	private void moveToNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) {
			currentPlayer = 0;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		moveToNextPlayer();
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == WINNING_SCORE);
	}
}
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    private int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    private int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
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
	    getPlaces()[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(getCurrentPlayer()) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[getCurrentPlayer()]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(getCurrentPlayer()) + " is getting out of the penalty box");
				getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] + roll;
				if (getPlaces()[getCurrentPlayer()] > 11) getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;
				
				System.out.println(players.get(getCurrentPlayer()) 
						+ "'s new location is " 
						+ getPlaces()[getCurrentPlayer()]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(getCurrentPlayer()) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] + roll;
			if (getPlaces()[getCurrentPlayer()] > 11) getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;
			
			System.out.println(players.get(getCurrentPlayer()) 
					+ "'s new location is " 
					+ getPlaces()[getCurrentPlayer()]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		if (getPlaces()[getCurrentPlayer()] == 0) return "Pop";
		if (getPlaces()[getCurrentPlayer()] == 4) return "Pop";
		if (getPlaces()[getCurrentPlayer()] == 8) return "Pop";
		if (getPlaces()[getCurrentPlayer()] == 1) return "Science";
		if (getPlaces()[getCurrentPlayer()] == 5) return "Science";
		if (getPlaces()[getCurrentPlayer()] == 9) return "Science";
		if (getPlaces()[getCurrentPlayer()] == 2) return "Sports";
		if (getPlaces()[getCurrentPlayer()] == 6) return "Sports";
		if (getPlaces()[getCurrentPlayer()] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[getCurrentPlayer()]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[getCurrentPlayer()]++;
				System.out.println(players.get(getCurrentPlayer()) 
						+ " now has "
						+ purses[getCurrentPlayer()]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				setCurrentPlayer(getCurrentPlayer() + 1);
				if (getCurrentPlayer() == players.size()) setCurrentPlayer(0);
				
				return winner;
			} else {
				setCurrentPlayer(getCurrentPlayer() + 1);
				if (getCurrentPlayer() == players.size()) setCurrentPlayer(0);
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[getCurrentPlayer()]++;
			System.out.println(players.get(getCurrentPlayer()) 
					+ " now has "
					+ purses[getCurrentPlayer()]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			setCurrentPlayer(getCurrentPlayer() + 1);
			if (getCurrentPlayer() == players.size()) setCurrentPlayer(0);
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(getCurrentPlayer())+ " was sent to the penalty box");
		inPenaltyBox[getCurrentPlayer()] = true;
		
		setCurrentPlayer(getCurrentPlayer() + 1);
		if (getCurrentPlayer() == players.size()) setCurrentPlayer(0);
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[getCurrentPlayer()] == 6);
	}
}

package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;
    
    public Player(String name) {
        this.name = name;
        this.place = 0;
        this.purse = 0;
        this.inPenaltyBox = false;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPlace() {
        return place;
    }
    
    public void setPlace(int place) {
        this.place = place;
    }
    
    public void moveForward(int steps, int boardSize) {
        place += steps;
        if (place >= boardSize) {
            place -= boardSize;
        }
    }
    
    public int getPurse() {
        return purse;
    }
    
    public void addCoin() {
        purse++;
    }
    
    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }
    
    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
    
    public boolean hasWon(int winningScore) {
        return purse == winningScore;
    }
}
package com.adaptionsoft.games.uglytrivia;

public enum QuestionCategory {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");
    
    private final String displayName;
    
    QuestionCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static QuestionCategory getCategoryForPosition(int position) {
        int categoryIndex = position % 4;
        switch (categoryIndex) {
            case 0: return POP;
            case 1: return SCIENCE;
            case 2: return SPORTS;
            case 3: return ROCK;
            default: return ROCK; 
        }
    }
}
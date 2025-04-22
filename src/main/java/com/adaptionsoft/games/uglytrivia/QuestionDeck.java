package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QuestionDeck {
    private static final int QUESTIONS_COUNT = 50;
    
    private Map<QuestionCategory, LinkedList<String>> questionsByCategory;
    
    public QuestionDeck() {
        initializeQuestions();
    }
    
    private void initializeQuestions() {
        questionsByCategory = new HashMap<>();
        
        for (QuestionCategory category : QuestionCategory.values()) {
            LinkedList<String> questions = new LinkedList<>();
            for (int i = 0; i < QUESTIONS_COUNT; i++) {
                questions.addLast(createQuestion(category, i));
            }
            questionsByCategory.put(category, questions);
        }
    }
    
    private String createQuestion(QuestionCategory category, int index) {
        return category.getDisplayName() + " Question " + index;
    }
    
    public String getNextQuestion(QuestionCategory category) {
        return questionsByCategory.get(category).removeFirst();
    }
}
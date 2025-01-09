package org.example.studycards;

import java.util.List;

public class Card {
    private String question;
    private String answer;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void edit(String question, String answer) {
        setQuestion(question);
        setAnswer(answer);
    }

    public boolean isAnswerCorrect(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer);
    }

    public boolean wasQuestionAskedBefore(List<String> askedQuestions) {
        return askedQuestions.contains(question);
    }

    public double calculateSuccessRate(int totalQuestions, int correctAnswers) {
        return (double) correctAnswers / totalQuestions * 100;
    }

    public String buildCardResponse(Integer randomCard) {
        String response = "[" + randomCard + "] ";
        response += "The random question was: " + this.question + " | ";
        response += "The answer is: " + this.answer;
        return response;
    }
}
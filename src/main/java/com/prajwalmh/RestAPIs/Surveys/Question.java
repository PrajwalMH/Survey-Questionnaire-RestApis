package com.prajwalmh.RestAPIs.Surveys;

import java.util.List;

public class Question {

    public Question(){

    }

    private String id;
    private String description;
    private List<String> options;

    private String correctAnswer;

    public Question(String id, String description, List<String> options, String correctAnswer) {
        this.id = id;
        this.description = description;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", options=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

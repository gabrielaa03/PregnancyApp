package com.gabrielaangebrandt.pregnancyapp.models.data_models;

;
import java.util.List;
import java.util.Map;

public class Question {
    private String title;
    private List<Map<String, String>> answers;

    public Question(String title, List<Map<String, String>> answers) {
        this.title = title;
        this.answers = answers;
    }

    public Question() {
    }

    public List<Map<String, String>> getList() {
        return answers;
    }

    public void setList(List<Map<String, String>> answers) {
        this.answers = answers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

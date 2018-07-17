package com.gabrielaangebrandt.pregnancyapp.models.data_models;

public class Meal {
    private String title;
    private String description;
    private String category;
    private String time;

    public Meal(String title, String description, String category, String time) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrtiption() {
        return description;
    }

    public void setDescrtiption(String descrtiption) {
        this.description = descrtiption;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimeForPreparation() {
        return time;
    }

    public void setTimeForPreparation(String timeForPreparation) {
        this.time = timeForPreparation;
    }
}

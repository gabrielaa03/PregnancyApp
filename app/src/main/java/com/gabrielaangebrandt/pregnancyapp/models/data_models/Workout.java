package com.gabrielaangebrandt.pregnancyapp.models.data_models;

public class Workout {
    private String title;
    private String description;
    private String category;
    private String months;

    public Workout(String title, String description, String months, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.months = months;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
}

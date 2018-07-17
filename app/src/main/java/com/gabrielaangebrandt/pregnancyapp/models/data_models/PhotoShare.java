package com.gabrielaangebrandt.pregnancyapp.models.data_models;


public class PhotoShare {
    private String username;
    private String image;

    public PhotoShare(String username, String image) {
        this.username = username;
        this.image = image;
    }

    public String getComment() {
        return username;
    }

    public void setComment(String comment) {
        this.username = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

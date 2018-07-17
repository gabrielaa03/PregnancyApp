package com.gabrielaangebrandt.pregnancyapp.models.data_models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Answer implements Parcelable {

    private String username;
    private String date;
    private String answer;

    public Answer() {

    }

    public Answer(String username, String answer, String date) {
        this.username = username;
        this.date = date;
        this.answer = answer;
    }

    public Answer(Parcel in) {
        username = in.readString();
        date = in.readString();
        answer = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String text) {
        this.answer = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(date);
        dest.writeString(answer);
    }
}

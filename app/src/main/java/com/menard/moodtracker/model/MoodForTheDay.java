package com.menard.moodtracker.model;

public class MoodForTheDay{



    private String mDate;
    private String mComment;
    private int mMood;

    // -- Constructor --
    public MoodForTheDay() {

    }


    // -- Getter and Setter --

    public String getDate() {
        return mDate;
    }

    public void setDate(String day) {
        mDate = day;
    }


    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public int getMood() {
        return mMood;
    }

    public void setMood(int mood) {
        mMood = mood;
    }
}

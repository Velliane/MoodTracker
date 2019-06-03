package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;

public class MoodForTheDay{



    private String mDate;
    @ColorRes
    private int mColor;
    private Mood mMood;
    private String mComment;
    private int mPage;

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

    @ColorRes
    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }


    public Mood getMood() {
        return mMood;
    }

    public void setMood(Mood mood) {
        mMood = mood;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }
}

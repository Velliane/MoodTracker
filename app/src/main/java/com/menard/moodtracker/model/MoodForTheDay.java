package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MoodForTheDay extends RealmObject {


    @PrimaryKey
    private String mDate;
    @ColorRes
    private int mColor;
    private String mComment;

    // -- Constructor --
    private MoodForTheDay(String day, @ColorRes int color, String comment) {
        mDate = day;
        mColor = color;
        mComment = comment;

    }

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





}

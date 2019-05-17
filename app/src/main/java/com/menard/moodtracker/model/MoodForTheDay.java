package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MoodForTheDay extends RealmObject {


    @PrimaryKey
    private int mId;
    private static String mDate;
    @ColorRes
    private static int mColor;
    private static String mComment;

    // -- Constructor --
    private MoodForTheDay(int id, String day, @ColorRes int color, String comment) {
        mId = id;
        mDate = day;
        mColor = color;
        mComment = comment;

    }

    public MoodForTheDay() {

    }


    // -- Getter and Setter --
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public static String getDate() {
        return mDate;
    }

    public void setDate(String day) {
        mDate = day;
    }

    @ColorRes
    public static int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public static String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }





}

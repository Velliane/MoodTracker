package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.menard.moodtracker.R;

public enum Mood {
    SAD(R.drawable.smiley_sad, R.color.faded_red),
    DISAPPOINTED(R.drawable.smiley_disappointed, R.color.warm_grey),
    NORMAL(R.drawable.smiley_normal, R.color.cornflower_blue_65),
    HAPPY(R.drawable.smiley_happy, R.color.light_sage),
    VERYHAPPY(R.drawable.smiley_super_happy, R.color.banana_yellow);



    @DrawableRes
    private int smileyRes;
    @ColorRes
    private int colorRes;

    Mood(@DrawableRes int smileyRes, @ColorRes int colorRes){
        this.smileyRes = smileyRes;
        this.colorRes = colorRes;
    }

    @DrawableRes
    public int getSmileyRes() {
        return smileyRes;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }
}

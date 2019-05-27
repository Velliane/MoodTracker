package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;

import com.menard.moodtracker.R;

public enum Mood {
    SAD(R.drawable.smiley_sad, R.color.faded_red, R.raw.normal, 0.2),
    DISAPPOINTED(R.drawable.smiley_disappointed, R.color.warm_grey, R.raw.normal, 0.4),
    NORMAL(R.drawable.smiley_normal, R.color.cornflower_blue_65, R.raw.normal, 0.6),
    HAPPY(R.drawable.smiley_happy, R.color.light_sage, R.raw.normal, 0.8),
    VERYHAPPY(R.drawable.smiley_super_happy, R.color.banana_yellow, R.raw.normal, 1);



    @DrawableRes
    private int smileyRes;
    @ColorRes
    private int colorRes;
    @RawRes
    private int audioRes;
    private double percentWidth;

    Mood(@DrawableRes int smileyRes, @ColorRes int colorRes, @RawRes int audioRes, double percent){
        this.smileyRes = smileyRes;
        this.colorRes = colorRes;
        this.audioRes = audioRes;
        this.percentWidth = percent;
    }

    @DrawableRes
    public int getSmileyRes() {
        return smileyRes;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }

    public int getAudioRes() {
        return audioRes;
    }

    public double getPercentWidth() {
        return percentWidth;
    }
}

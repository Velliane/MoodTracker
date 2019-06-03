package com.menard.moodtracker.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;

import com.menard.moodtracker.R;

public enum Mood {
    SAD(R.drawable.smiley_sad, R.color.faded_red, R.raw.sad, 0.2f),
    DISAPPOINTED(R.drawable.smiley_disappointed, R.color.warm_grey, R.raw.disappointed, 0.4f),
    NORMAL(R.drawable.smiley_normal, R.color.cornflower_blue_65, R.raw.normal, 0.6f),
    HAPPY(R.drawable.smiley_happy, R.color.light_sage, R.raw.happy, 0.8f),
    VERYHAPPY(R.drawable.smiley_super_happy, R.color.banana_yellow, R.raw.super_happy, 1);



    @DrawableRes
    private int smileyRes;
    @ColorRes
    private int colorRes;
    @RawRes
    private int audioRes;
    private float percentWidth;

    Mood(@DrawableRes int smileyRes, @ColorRes int colorRes, @RawRes int audioRes, float percent){
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

    @RawRes
    public int getAudioRes() {
        return audioRes;
    }

    public float getPercentWidth() {
        return percentWidth;
    }
}

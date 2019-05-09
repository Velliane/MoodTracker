package com.menard.moodtracker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.menard.moodtracker.fragments.PageFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int[] colors;
    private int[] images;

    public ViewPagerAdapter(FragmentManager fm, int[] colors, int[]images) {
        super(fm);
        this.colors = colors;
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position, this.colors[position], this.images[position]);
    }



    @Override
    public int getCount() {
        return 5;
    }
}

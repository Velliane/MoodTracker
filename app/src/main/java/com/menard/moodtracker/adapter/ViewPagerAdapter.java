package com.menard.moodtracker.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.menard.moodtracker.fragments.PageFragment;
import com.menard.moodtracker.model.Mood;

public class ViewPagerAdapter extends FragmentPagerAdapter {



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return PageFragment.newInstance(position, Mood.values()[position].getColorRes(), Mood.values()[position].getSmileyRes());
    }



    @Override
    public int getCount() {
        return Mood.values().length;
    }
}

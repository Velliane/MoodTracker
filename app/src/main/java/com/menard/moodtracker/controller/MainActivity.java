package com.menard.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.menard.moodtracker.Database.DataHelper;
import com.menard.moodtracker.R;
import com.menard.moodtracker.View.VerticalViewPager;
import com.menard.moodtracker.adapter.ViewPagerAdapter;
import com.menard.moodtracker.fragments.AlertDialogFragmentComment;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogFragmentComment.Listener {


    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;
    /** Realm database */
    public Realm mRealm;
    /** MoodForTheDay */
    public MoodForTheDay mMoodForTheDay;
    /** Date */
    private String mDate;

    SharedPreferences mSharedPreferences;
    public final String PREF_KEY_MAINSHARED = "PREF_KEY_MAINSHARED";

    /** Bundle for saveInstance */
    public String BUNDLE_CURRENT_PAGE = "BUNDLE_CURRENT_PAGE";
    /** SQLite Database */
    private DataHelper mDataHelper;

    VerticalViewPagerListener listener;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataHelper = new DataHelper(this);

        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);

        mSharedPreferences = getSharedPreferences(PREF_KEY_MAINSHARED, MODE_PRIVATE);

        //-- Instantiate ViewPager and set Adapter --
        pager = findViewById(R.id.activity_main_viewpager);
        listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //-- Set the current page --
        if(BUNDLE_CURRENT_PAGE == null) {
            pager.setCurrentItem((Mood.values().length) / 2);
        }else
            pager.setCurrentItem(listener.getCurrentPage());


        //-- 310ABP initialisation --
        AndroidThreeTen.init(this);

        mDataHelper.getAllMoodDay();


        //-- Test --
        if (mMoodForTheDay == mDataHelper.getMoodDay(getDateDay())){
            mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            onCommentSelected(mMoodForTheDay.getComment());
            mDataHelper.updateMoodDay(mDate, mMoodForTheDay);
        } else {
            mMoodForTheDay = new MoodForTheDay();
            mMoodForTheDay.setDate(getDateDay());
            mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            onCommentSelected(mMoodForTheDay.getComment());
            mDataHelper.addMoodDay(mMoodForTheDay);
        }


    }

    /**
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if (v == mBtnAddComments) {
           new AlertDialogFragmentComment().newInstance().show(getSupportFragmentManager(), "AlertDialog");
           mDataHelper.addComment(mMoodForTheDay.getComment(), mMoodForTheDay.getDate());
        }

        if (v == mBtnShowHistory) {
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }

    }


    /**
     * Return the date of the current Day
     * @return the date
     */
    public String getDateDay() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        System.out.println(today);
        return today.toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onCommentSelected(String comment) {
        mDataHelper.addComment(comment, mMoodForTheDay.getDate());
    }

    @ColorRes
    private int getColorMood(int position){
       return Mood.values()[position].getColorRes();
    }



    /**
     * Listener for the VerticalViewPager
     */
    public class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener {

        public int currentPage;

        @Override
        public void onPageSelected(int position) {
           mSharedPreferences.edit().putInt(BUNDLE_CURRENT_PAGE, position).apply();
        }

        public int getCurrentPage() {
           return currentPage = mSharedPreferences.getInt(BUNDLE_CURRENT_PAGE, 2);
        }


    }


}

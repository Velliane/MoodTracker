package com.menard.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
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

    /** Shared Preferences */
    SharedPreferences mSharedPreferences;
    /** Key for Shared Preferences */
    public final String PREF_KEY_MAINSHARED = "PREF_KEY_MAINSHARED";
    public String PREF_KEY_CURRENT_PAGE = "PREF_KEY_CURRENT_PAGE";
    private String PREF_KEY_COMMENT = "PREK_KEY_COMMENT";
    private String PREF_KEY_TODAY_DATE = "PREF_KEY_TODAY_DATE";
    /** SQLite Database */
    private DataHelper mDataHelper;

    /** VerticalViewPager Listener */
    VerticalViewPagerListener listener;
    /** ViewPager */
    ViewPager pager;


    String today;


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
        //if(PREF_KEY_CURRENT_PAGE == null) {
            //pager.setCurrentItem((Mood.values().length) / 2);
        //}else
            //pager.setCurrentItem(listener.getCurrentPage());

        //-- Get the current date from the Shared Preferences --
        if (!PREF_KEY_TODAY_DATE.equals(LocalDate.now(ZoneId.systemDefault()).toString())){
            pager.setCurrentItem((Mood.values().length) / 2);
            today = getDateDay();
        } else {
            today = mSharedPreferences.getString(PREF_KEY_TODAY_DATE, null);
            pager.setCurrentItem(listener.getCurrentPage());
        }

        //-- 310ABP initialisation --
        AndroidThreeTen.init(this);


        //-- Test --
        if (mMoodForTheDay == mDataHelper.getMoodDay(today)){
            mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            //mMoodForTheDay.setComment(getCommentFromSharefPref());
            mDataHelper.addComment(getCommentFromSharefPref(), mMoodForTheDay.getDate());
            mDataHelper.updateMoodDay(mMoodForTheDay.getDate(), mMoodForTheDay);
        } else {
            mMoodForTheDay = new MoodForTheDay();
            mMoodForTheDay.setDate(today);
            mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            onCommentSelected(getCommentFromSharefPref());
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
          // mDataHelper.addComment(mMoodForTheDay.getComment(), mMoodForTheDay.getDate());
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
        //-- Save the current day in the Shared Preferences --
        mSharedPreferences.edit().putString(PREF_KEY_TODAY_DATE, today.toString()).apply();
        return today.toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    /**
     * Saving the comment get from the AlertDialogFragmentComment
     * @param comment the comment
     */
    @Override
    public void onCommentSelected(String comment) {
        mSharedPreferences.edit().putString(PREF_KEY_COMMENT, comment).apply();
    }


    public String getCommentFromSharefPref(){
       return mSharedPreferences.getString(PREF_KEY_COMMENT, null);
    }

    @ColorRes
    private int getColorMood(int position){
       return Mood.values()[position].getColorRes();
    }



    /**
     * Listener for the VerticalViewPager
     */
    public class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener {

        int currentPage;

        @Override
        public void onPageSelected(int position) {
           mSharedPreferences.edit().putInt(PREF_KEY_CURRENT_PAGE, position).apply();
        }

        int getCurrentPage() {
           return currentPage = mSharedPreferences.getInt(PREF_KEY_CURRENT_PAGE, 2);
        }


    }


}

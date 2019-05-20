package com.menard.moodtracker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
    /** Date */
    private String mDate;

    /** SQLite Database */
    private DataHelper mDataHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataHelper = new DataHelper(this);

        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);


        //-- Instantiate ViewPager and set Adapter --
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        VerticalViewPagerListener listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem((Mood.values().length) / 2);

        //-- Realm initialisation --
        //Realm.init(this);
        //mRealm = Realm.getDefaultInstance();

        //-- 310ABP initialisation --
        AndroidThreeTen.init(this);

        //-- Get today's date --
        mDate = getDateDay();
        // if (mMoodForTheDay.getDate() != mDate){
        // mMoodForTheDay = new MoodForTheDay()
        // }

        //-- Select the MoodForTheDay with the today's date --
        //-- If doesn't exist, create new --
        mDataHelper.open();
        if (mDataHelper.getMoodDay(mDate) == null) {
            mMoodForTheDay = new MoodForTheDay();
            mMoodForTheDay.setDate(mDate);
            mDataHelper.addMoodDay(mMoodForTheDay);
        } else {
            mMoodForTheDay = mDataHelper.getMoodDay(mDate);
        }


    }

    /**
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if (v == mBtnAddComments) {
           new AlertDialogFragmentComment().onCreateDialog(null).show();
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

    }


    /**
     * Listener for the VerticalViewPager
     */
    public class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        int getCurrentPage() {
            return currentPage;
        }
    }


}

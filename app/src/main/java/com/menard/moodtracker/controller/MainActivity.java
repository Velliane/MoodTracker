package com.menard.moodtracker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.menard.moodtracker.R;
import com.menard.moodtracker.View.VerticalViewPager;
import com.menard.moodtracker.adapter.ViewPagerAdapter;
import com.menard.moodtracker.model.AlertDialogComment;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;

import org.threeten.bp.Instant;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;

    /** Realm database*/
    public Realm mRealm;
    /** MoodForTheDay */
    public MoodForTheDay mMoodForTheDay;

    /** Date */
    private String mDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);


        //-- Instantiate ViewPager and set Adapter --
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        VerticalViewPagerListener listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem((Mood.values().length )/2);

        //-- Realm initialisation --
        //Realm.init(this);
        //mRealm = Realm.getDefaultInstance();

        //-- 310ABP initialisation --
        AndroidThreeTen.init(this);

        //-- Get the date of the day --
        mDate = Instant.now().toString();
        // if (mMoodForTheDay.getDate() != mDate){
        // mMoodForTheDay = new MoodForTheDay()
        // }

        mMoodForTheDay = new MoodForTheDay();
        mMoodForTheDay.setColor(getColorPage(listener.getCurrentPage()));
    }

    /**
     *
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if(v == mBtnAddComments){
            AlertDialogComment.showAlertDialog(this);
        }

        if(v == mBtnShowHistory){
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }

    }


    public int getColorPage(int currentPage){
        int colorPage = 0;
        switch (currentPage){
            case 0:
                colorPage = R.color.faded_red;
            case 1:
                colorPage = R.color.warm_grey;
            case 2:
                colorPage = R.color.cornflower_blue_65;
            case 3:
                colorPage = R.color.light_sage;
            case 4:
                colorPage = R.color.banana_yellow;
        }
        return colorPage;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null){
            mRealm.close();
        }
    }


    /**
     * Listener for the VerticalViewPager
     */
    public class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener{

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

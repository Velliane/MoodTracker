package com.menard.moodtracker.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.menard.moodtracker.database.BaseSQLite;
import com.menard.moodtracker.R;
import com.menard.moodtracker.view.VerticalViewPager;
import com.menard.moodtracker.adapter.ViewPagerAdapter;
import com.menard.moodtracker.fragments.AlertDialogFragmentComment;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogFragmentComment.Listener {


    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;
    /** Button Send Message */
    private ImageButton mBtnSendMessage;

    /** ViewPager */
    private ViewPager pager;
    /** BaseSQLite */
    private BaseSQLite mBaseSQLite;

    private String today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBaseSQLite = new BaseSQLite(this);

        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);
        mBtnSendMessage = findViewById(R.id.activity_send_message_btn);
        mBtnSendMessage.setOnClickListener(this);

        //-- 310ABP initialisation --
        AndroidThreeTen.init(this);


        //-- Instantiate ViewPager and set Adapter --
        pager = findViewById(R.id.activity_main_viewpager);
        VerticalViewPagerListener listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


        today = getDateDay();
        //-- Test --

        MoodForTheDay moodForTheDay = mBaseSQLite.getMoodDay(today);
        mBaseSQLite.addMoodDay(moodForTheDay);
    }

    /**
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if (v == mBtnAddComments) {
           new AlertDialogFragmentComment().newInstance().show(getSupportFragmentManager(), "AlertDialog");
        }

        if (v == mBtnShowHistory) {
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }

        if(v == mBtnSendMessage) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
            intent.putExtra(Intent.EXTRA_TEXT, mBaseSQLite.getMoodDay(today).getComment());
            //String path= "android.resource://com.menard.moodtracker"+R.drawable.smiley_super_happy;
            //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            startActivity(intent);
        }

    }


    /**
     * Return the date of the current Day
     * @return the date
     */
    private String getDateDay() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        System.out.println(today);
        return today.toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Saving the comment get from the AlertDialogFragmentComment
     * @param comment the comment
     */
    @Override
    public void onCommentSelected(String comment) {
        mBaseSQLite.addComment(comment, today);
    }





    /**
     * Listener for the VerticalViewPager
     */
    private class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            mBaseSQLite.addColor(position, today);
            //pager.setCurrentItem(position);
        }



    }


}

package com.menard.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.menard.moodtracker.Database.BaseSQLite;
import com.menard.moodtracker.R;
import com.menard.moodtracker.View.VerticalViewPager;
import com.menard.moodtracker.adapter.ViewPagerAdapter;
import com.menard.moodtracker.fragments.AlertDialogFragmentComment;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.net.URI;

import io.realm.Realm;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AlertDialogFragmentComment.Listener {


    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;
    /** Button Send Message */
    private ImageButton mBtnSendMessage;
    /** Realm database */
    public Realm mRealm;
    /** MoodForTheDay */
    public MoodForTheDay mMoodForTheDay;

    /** Key for Shared Preferences */

    public String PREF_KEY_CURRENT_PAGE = "PREF_KEY_CURRENT_PAGE";
    /** SQLite Database */
    private BaseSQLite mBaseSQLite;

    /** VerticalViewPager Listener */
    VerticalViewPagerListener listener;
    /** ViewPager */
    ViewPager pager;


    String today;


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
        listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


        //-- Set the current page --
        if(PREF_KEY_CURRENT_PAGE == null) {
            pager.setCurrentItem((Mood.values().length) / 2);
        }else
            //pager.setCurrentItem(listener.getCurrentPage());

        //-- Get the current date from the Shared Preferences --
        //if (!PREF_KEY_TODAY_DATE.equals(LocalDate.now(ZoneId.systemDefault()).toString())){
            //pager.setCurrentItem((Mood.values().length) / 2);
            //today = getDateDay();
            //listener.onPageSelected(pager.getCurrentItem());
        //} else {
            //today = mSharedPreferences.getString(PREF_KEY_TODAY_DATE, null);
            //if(PREF_KEY_CURRENT_PAGE != null)
            //pager.setCurrentItem(listener.getCurrentPage());
        //}

        today = getDateDay();



        //-- Test --
        if (mMoodForTheDay == mBaseSQLite.getMoodDay(today)){
            //mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            //mMoodForTheDay.setComment(getCommentFromSharefPref());
            //mDataHelper.addComment(getCommentFromSharefPref(), mMoodForTheDay.getDate());
            mBaseSQLite.updateMoodDay(mMoodForTheDay.getDate(), mMoodForTheDay);
        } else {
            mMoodForTheDay = new MoodForTheDay();
            mMoodForTheDay.setDate(today);
            //mMoodForTheDay.setColor(getColorMood(listener.getCurrentPage()));
            //onCommentSelected(getCommentFromSharefPref());
            mBaseSQLite.addMoodDay(mMoodForTheDay);

        }


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
            //composeSMS("Coucou", uri);
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
            intent.putExtra(Intent.EXTRA_TEXT, "Coucou!! Petit test <3");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("smiley_super_happy.png"));
            startActivity(intent);
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
        //mettre à jour le comment du jour avec SQL
    }

    public void composeSMS(@Nullable String message, Uri uri){
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_STREAM, image);
        intent.putExtra("sms body", message);
        //if(intent.resolveActivity(getPackageManager()) != null){
        startActivity(intent);

    }




    /**
     * Listener for the VerticalViewPager
     */
    public class VerticalViewPagerListener extends VerticalViewPager.SimpleOnPageChangeListener {

        int currentPage;

        @Override
        public void onPageSelected(int position) {
            //mettre à jour l'humeur du jour avec SQL
        }

        //int getCurrentPage() {
        //}


    }


}

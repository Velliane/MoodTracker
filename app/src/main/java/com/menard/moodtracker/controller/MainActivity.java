package com.menard.moodtracker.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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
    /** BaseSQLite */
    private BaseSQLite mBaseSQLite;
    /** Today's date */
    private String today;
    /** Vertical ViewPager */
    private VerticalViewPager pager;
    private MoodForTheDay moodForTheDay;

    @Override
    @SuppressWarnings("deprecation")
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
        //-- Get today's date
        today = getDateDay();

        //-- Instantiate ViewPager and set Adapter and Listener--
        pager = findViewById(R.id.activity_main_viewpager);
        VerticalViewPagerListener listener = new VerticalViewPagerListener();
        pager.setOnPageChangeListener(listener);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(mBaseSQLite.getPage(today));

        //-- Adding or updating the mood for the day
        moodForTheDay = mBaseSQLite.getMoodDay(today);
        mBaseSQLite.addMoodDay(moodForTheDay, pager.getCurrentItem());
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
            //Uri uri = Uri.parse(String.valueOf(Mood.values()[pager.getCurrentItem()].getSmileyRes()));
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
            intent.putExtra(Intent.EXTRA_TEXT, mBaseSQLite.getMoodDay(today).getComment());
            //intent.putExtra(Intent.EXTRA_STREAM, uri);
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
    protected void onResume() {
        super.onResume();
        String date = getDateDay();
        if (!date.equals(today)) {
            today = date;
            pager.setCurrentItem(3);
        }
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
            mBaseSQLite.addPage(today, position);

            final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,Mood.values()[position].getAudioRes());
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
        }






    }


}

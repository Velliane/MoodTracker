package com.menard.moodtracker.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {

    /** View RelativeLayout */
    private RelativeLayout mRelativeLayout;
    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;

    private int[] imagesPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.activity_main_layout);
        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);

        imagesPager = new int[]{R.drawable.smiley_sad, R.drawable.smiley_disappointed, R.drawable.smiley_normal, R.drawable.smiley_happy, R.drawable.smiley_super_happy};
        // Instantiate ViewPager and set Adapter
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager), imagesPager));
        pager.setCurrentItem(2);

    }

    /**
     *
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if(v == mBtnAddComments){
            //add a comment
        }

        if(v == mBtnShowHistory){
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }

    }



    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

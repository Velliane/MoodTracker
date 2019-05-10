package com.menard.moodtracker.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.menard.moodtracker.AlertDialogComment;
import com.menard.moodtracker.Mood;
import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAddComments = findViewById(R.id.activity_main_comments_btn);
        mBtnAddComments.setOnClickListener(this);
        mBtnShowHistory = findViewById(R.id.activity_main_history_btn);
        mBtnShowHistory.setOnClickListener(this);


        // Instantiate ViewPager and set Adapter
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem((Mood.values().length )/2);

    }

    /**
     *
     * @param v the button
     */
    @Override
    public void onClick(View v) {

        if(v == mBtnAddComments){
            AlertDialogComment.showAlertDialo(this);
        }

        if(v == mBtnShowHistory){
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }

    }


}

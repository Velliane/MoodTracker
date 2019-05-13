package com.menard.moodtracker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.menard.moodtracker.R;
import com.menard.moodtracker.model.AlertDialogComment;
import com.menard.moodtracker.adapter.ViewPagerAdapter;
import com.menard.moodtracker.model.Mood;

import org.threeten.bp.ZoneId;

import io.realm.Realm;
import io.realm.internal.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static Preference showLocal;
    /** Button add comments */
    private ImageButton mBtnAddComments;
    /** Button Show History */
    private ImageButton mBtnShowHistory;

    /** Realm database*/
    public Realm mRealm;



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

        // Realm initialisation
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        // 310ABP initialisation
        AndroidThreeTen.init(this);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null){
            mRealm.close();
        }
    }


}

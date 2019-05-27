package com.menard.moodtracker.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.Database.BaseSQLite;
import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.HistoryAdapter;
import com.menard.moodtracker.model.MoodForTheDay;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    /** RecyclerView */
    RecyclerView mHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //-- DataHelper --
        BaseSQLite baseSQLite = new BaseSQLite(this);

        //-- Recycler View --
        mHistory = findViewById(R.id.history_list);
        mHistory.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mHistory.setLayoutManager(mLayoutManager);

        //-- RecyclerView adapter --
        List<MoodForTheDay> moods = baseSQLite.getAllMoodDay();
        HistoryAdapter adapter = new HistoryAdapter(moods);
        mHistory.setAdapter(adapter);
        //adapter.setData(mMoods);

    }

}

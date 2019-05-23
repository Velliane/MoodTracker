package com.menard.moodtracker.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.Database.DataHelper;
import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.HistoryAdapter;
import com.menard.moodtracker.model.MoodForTheDay;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    /** RecyclerView */
    RecyclerView mHistory;
    /** LayoutManager */
    LinearLayoutManager mLayoutManager;
    /** List of the Moods */
    private List<MoodForTheDay> mMoods;
    /** SQLite Database */
    private DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mDataHelper = new DataHelper(this);

        // Recycler View
        mHistory = findViewById(R.id.history_list);
        mHistory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mHistory.setLayoutManager(mLayoutManager);

        // RecyclerView adapter
        mMoods = mDataHelper.getAllMoodDay();
        HistoryAdapter adapter = new HistoryAdapter(this, mMoods);
        mHistory.setAdapter(adapter);
        //adapter.setData(mMoods);

    }

}

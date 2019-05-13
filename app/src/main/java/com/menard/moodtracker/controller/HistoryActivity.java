package com.menard.moodtracker.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.HistoryAdapter;
import com.menard.moodtracker.model.Day;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mHistory;

    LinearLayoutManager mLayoutManager;

    private ArrayList<Day> mMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Recycler View
        mHistory = findViewById(R.id.history_list);
        mHistory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mHistory.setLayoutManager(mLayoutManager);

        // RecyclerView adapter
        mMoods = Day.getMoods();
        HistoryAdapter adapter = new HistoryAdapter(this, mMoods);
        mHistory.setAdapter(adapter);

    }


}

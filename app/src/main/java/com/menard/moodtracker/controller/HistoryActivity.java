package com.menard.moodtracker.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.database.BaseSQLite;
import com.menard.moodtracker.R;
import com.menard.moodtracker.adapter.HistoryAdapter;
import com.menard.moodtracker.model.MoodForTheDay;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //-- DataHelper --
        BaseSQLite baseSQLite = new BaseSQLite(this);

        //-- Recycler View --
        RecyclerView history = findViewById(R.id.history_list);
        history.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        history.setLayoutManager(mLayoutManager);

        //-- RecyclerView adapter --
        List<MoodForTheDay> moods = baseSQLite.getAllMoodDay();
        HistoryAdapter adapter = new HistoryAdapter(moods);
        history.setAdapter(adapter);


    }

}

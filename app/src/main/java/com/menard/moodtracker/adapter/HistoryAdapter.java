package com.menard.moodtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.R;
import com.menard.moodtracker.model.Day;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder> {

    private ArrayList<Day> Moods;


    public HistoryAdapter(ArrayList<Day> items){
        Moods = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.history_moods, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ListViewHolder myViewHolder, int position) {
        Moods = Day.getMoods();
        for(int i = 0; i<Moods.size(); i++){
            myViewHolder.moodDay.setText(Day.loadComment());
            myViewHolder.moodDay.setBackgroundColor(Day.getColor());
        }

    }

    @Override
    public int getItemCount() {
        return Moods.size();
    }


    /**
     *
     */
    class ListViewHolder extends RecyclerView.ViewHolder {

        private final TextView moodDay;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            moodDay = itemView.findViewById(R.id.history_txt_view);
    }
}
}

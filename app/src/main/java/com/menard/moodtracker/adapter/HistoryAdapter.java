package com.menard.moodtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.R;
import com.menard.moodtracker.model.Day;

import org.threeten.bp.Instant;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder> {

    private Context mContext;
    private ArrayList<Day> Moods;


    public HistoryAdapter(Context context, ArrayList<Day> items){
        mContext = context;
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
            myViewHolder.moodDay.setText(Instant.now().toString());
            myViewHolder.moodDay.setBackgroundColor(Day.getColor());
            if (Day.loadComment()!= null){
                mContext = myViewHolder.btnComment.getContext();
                myViewHolder.btnComment.setVisibility(View.VISIBLE);
                myViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, Day.loadComment(), Toast.LENGTH_LONG).show();
                    }
                });
            }
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
        private final ImageButton btnComment;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            moodDay = itemView.findViewById(R.id.history_txt_view);
            btnComment = itemView.findViewById(R.id.history_btn_comments);
    }
}
}

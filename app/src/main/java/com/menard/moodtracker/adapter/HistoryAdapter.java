package com.menard.moodtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.DataHelper;
import com.menard.moodtracker.R;
import com.menard.moodtracker.model.MoodForTheDay;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder> {

    private Context mContext;
    private List<MoodForTheDay> Moods;


    public HistoryAdapter(Context context, List<MoodForTheDay> items){
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

        Moods = DataHelper.getAllMoods();
        if (Moods != null) {
            for (int i = 0; i < Moods.size(); i++) {
                myViewHolder.moodDate.setText(MoodForTheDay.getDate());
                myViewHolder.mLayout.setBackgroundColor(mContext.getResources().getColor(MoodForTheDay.getColor()));
                if (MoodForTheDay.getComment() != null) {
                    mContext = myViewHolder.btnComment.getContext();
                    myViewHolder.btnComment.setVisibility(View.VISIBLE);
                    myViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, MoodForTheDay.getComment(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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

        private final TextView moodDate;
        private final ImageButton btnComment;
        private final LinearLayout mLayout;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.history_layout);
            moodDate = itemView.findViewById(R.id.history_txt_view);
            btnComment = itemView.findViewById(R.id.history_btn_comments);
    }
}
}

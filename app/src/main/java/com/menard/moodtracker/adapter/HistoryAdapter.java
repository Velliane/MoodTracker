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
import com.menard.moodtracker.R;
import com.menard.moodtracker.model.Mood;
import com.menard.moodtracker.model.MoodForTheDay;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder> {

    private List<MoodForTheDay> Moods;



    public HistoryAdapter(List<MoodForTheDay> items) {
        Moods = items;
    }

    public void setData(List<MoodForTheDay> items) {
        Moods = items;
        notifyDataSetChanged();
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

        final MoodForTheDay mMoodForTheDay = Moods.get(position);


        myViewHolder.moodDate.setText(setDateText(mMoodForTheDay.getDate(), myViewHolder.itemView.getContext()));
        myViewHolder.mLayout.setBackgroundResource(mMoodForTheDay.getColor());
        ViewGroup.LayoutParams layoutParams = (new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, Mood.values()[mMoodForTheDay.getPage()].getPercentWidth()));

        myViewHolder.mLayout.setLayoutParams(layoutParams);

        // -- If comment not null --
        if (mMoodForTheDay.getComment() != null) {
            myViewHolder.btnComment.setVisibility(View.VISIBLE);
            myViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), mMoodForTheDay.getComment(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private String setDateText(String date, Context context) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        String dayText = "";
        if (date.equals(today.minusDays(1).toString())) {
            dayText = context.getResources().getString(R.string.history_yesterday);
        } else if (date.equals(today.minusDays(2).toString())) {
            dayText = context.getResources().getString(R.string.history_before_yesterday);
        }else if(date.equals(today.minusDays(3).toString())) {
            dayText = context.getResources().getString(R.string.history_three_days_ago);
        }else if (date.equals(today.minusDays(4).toString())) {
            dayText = context.getResources().getString(R.string.history_four_days_ago);
        }else if (date.equals(today.minusDays(5).toString())) {
            dayText = context.getResources().getString(R.string.history_five_day_ago);
        }else if (date.equals(today.minusDays(6).toString())) {
            dayText = context.getResources().getString(R.string.history_six_days_ago);
        }else if (date.equals(today.minusDays(7).toString())) {
            dayText = context.getResources().getString(R.string.history_a_week_ago);
        }

        return dayText;
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

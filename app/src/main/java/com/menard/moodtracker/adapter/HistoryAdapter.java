package com.menard.moodtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.menard.moodtracker.R;
import com.menard.moodtracker.model.MoodForTheDay;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder> {

    private Context mContext;
    private List<MoodForTheDay> Moods;
    private MoodForTheDay mMoodForTheDay;


    public HistoryAdapter(Context context, List<MoodForTheDay> items) {
        mContext = context;
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

        mMoodForTheDay = Moods.get(position);

        int width = myViewHolder.mLayout.getWidth();
        @ColorRes int color = mMoodForTheDay.getColor();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) myViewHolder.mLayout.getLayoutParams();

        myViewHolder.moodDate.setText(setDateText(mMoodForTheDay.getDate()));
        myViewHolder.mLayout.setBackgroundResource(mMoodForTheDay.getColor());
        switch (color) {
            case R.color.faded_red:
                layoutParams = new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                break;
            case R.color.warm_grey:
                layoutParams = new LinearLayout.LayoutParams((width / 5) * 2, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                break;
            case R.color.cornflower_blue_65:
                layoutParams = new LinearLayout.LayoutParams((width / 5) * 3, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                break;
            case R.color.light_sage:
                layoutParams = new LinearLayout.LayoutParams((width / 5) * 4, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                break;
            case R.color.banana_yellow:
                layoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                break;
        }
        //--

        myViewHolder.mLayout.setLayoutParams(layoutParams);

        // -- If comment not null --
        if (mMoodForTheDay.getComment() != null) {
            mContext = myViewHolder.btnComment.getContext();
            myViewHolder.btnComment.setVisibility(View.VISIBLE);
            myViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mMoodForTheDay.getComment(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private String setDateText(String date) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        String dayText = "";
        if (date.equals(today.minusDays(1).toString())) {
            dayText = "Hier";
        } else if (date.equals(today.minusDays(2).toString())) {
            dayText = "Avant-hier";
        }else if(date.equals(today.minusDays(3).toString())) {
            dayText = "Il y a trois jours";
        }else if (date.equals(today.minusDays(4).toString())) {
            dayText = "Il y a quatre jours";
        }else if (date.equals(today.minusDays(5).toString())) {
            dayText = "Il y a cinq jours";
        }else if (date.equals(today.minusDays(6).toString())) {
            dayText = "Il y a six jours";
        }else if (date.equals(today.minusDays(7).toString())) {
            dayText = "Il y a sept jours";
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

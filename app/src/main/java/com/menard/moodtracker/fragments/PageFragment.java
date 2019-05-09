package com.menard.moodtracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.menard.moodtracker.R;

public class PageFragment extends Fragment {


    // Keys for Bundle
    private static final String KEY_POSITION = "position";
    private static final String KEY_COLOR = "color";



    public PageFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param position position
     * @param color color
     * @return A new instance of fragment PageFragment.
     */
    public static PageFragment newInstance(int position, int color) {
        // New fragment
        PageFragment fragment = new PageFragment();
        // Create bundle and adding data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        // Implement widgets
        RelativeLayout relativeLayout = view.findViewById(R.id.fragment_page_rootview);
        ImageView imgView = view.findViewById(R.id.activity_main_img_view);

        // Get data from Bundle
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        // Update widget
        switch (position){
            case -2:
                imgView.setImageResource(R.drawable.smiley_super_happy);
                relativeLayout.setBackgroundColor(Color.BLACK);
            case -1:
                imgView.setImageResource(R.drawable.smiley_happy);
            case 0:
                imgView.setImageResource(R.drawable.smiley_normal);
                relativeLayout.setBackgroundColor(Color.CYAN);
            case 1:
                imgView.setImageResource(R.drawable.smiley_sad);
            case 2:
                imgView.setImageResource(R.drawable.smiley_disappointed);
        }


        return view;
    }


}

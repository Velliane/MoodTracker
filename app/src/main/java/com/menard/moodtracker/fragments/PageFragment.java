package com.menard.moodtracker.fragments;

import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
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
    private static final String KEY_IMAGE = "image";



    public PageFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param position position
     * @return A new instance of fragment PageFragment.
     */
    public static PageFragment newInstance(int position, @ColorRes int color, int image) {
        // New fragment
        PageFragment fragment = new PageFragment();
        // Create bundle and adding data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        args.putInt(KEY_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        // Implement widgets
        RelativeLayout relativeLayout = view.findViewById(R.id.fragment_page_rootview);
        ImageView imgView = view.findViewById(R.id.activity_main_img_view);

        // Get data from Bundle
        int position = getArguments().getInt(KEY_POSITION, -1);
        @ColorRes int color = getArguments().getInt(KEY_COLOR,-1);
        int image = getArguments().getInt(KEY_IMAGE, -1);

        // Update widget
        relativeLayout.setBackgroundResource(color);
        imgView.setImageResource(image);

        Log.e(getClass().getSimpleName(), "Page number"+position);


        return view;
    }


}

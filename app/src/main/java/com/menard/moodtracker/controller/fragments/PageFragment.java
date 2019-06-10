package com.menard.moodtracker.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.menard.moodtracker.R;

public class PageFragment extends Fragment {


    /**
     * Keys for Bundle
     */
    private static final String KEY_COLOR = "color";
    private static final String KEY_IMAGE = "image";


    public PageFragment() {
    }

    /**
     * @return A new instance of fragment PageFragment.
     */
    public static PageFragment newInstance(@ColorRes int color, int image) {
        PageFragment fragment = new PageFragment();
        //-- Create bundle and adding data --
        Bundle args = new Bundle();

        args.putInt(KEY_COLOR, color);
        args.putInt(KEY_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.fragment_page_rootview);
        ImageView imgView = view.findViewById(R.id.activity_main_img_view);

        //-- Get data from Bundle --
        assert getArguments() != null;

        @ColorRes int color = getArguments().getInt(KEY_COLOR, -1);
        @DrawableRes int image = getArguments().getInt(KEY_IMAGE, -1);

        relativeLayout.setBackgroundResource(color);
        imgView.setImageResource(image);


        return view;
    }


}

package com.menard.moodtracker.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(@NonNull Context context) {
        this(context, null);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }



    /**
     * Method to flip the X and Y
     * @param motionEvent MotionEvent
     * @return MotionEvent
     */
    private MotionEvent flipXY(MotionEvent motionEvent){
        float width = getWidth();
        float height = getHeight();

        float x = (motionEvent.getY() / height) * width;
        float y = (motionEvent.getX() / width) * height;

        motionEvent.setLocation(x, y);
        return motionEvent;

    }

    /**
     * Call method flipYX when touch the screen
     * @param motionEvent MotionEvent
     * @return boolean
     */
    public boolean onTouchEvent(MotionEvent motionEvent){
        boolean toHandle = super.onTouchEvent(flipXY(motionEvent));
        flipXY(motionEvent);
        return toHandle;
    }


    //-- PageTransformer --
    private static final class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(View view, float position) {
            final int pageWidth = view.getWidth();
            final int pageHeight = view.getHeight();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 1) {
                view.setAlpha(1);
                view.setTranslationX(pageWidth * -position);
                float yPosition = position * pageHeight;
                view.setTranslationY(yPosition);
            } else {
                view.setAlpha(0);
            }
        }
    }


}

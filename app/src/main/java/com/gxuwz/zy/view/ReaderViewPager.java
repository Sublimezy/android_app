package com.gxuwz.zy.view;


import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import java.lang.reflect.Field;


public class ReaderViewPager extends ViewPager{

    public ReaderViewPager(Context context) {
        this(context, null);
    }


    public ReaderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setReadEffect();

        setScrollerDuration();
    }

    private void setScrollerDuration() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(),
                    new DecelerateInterpolator());
            field.set(this, scroller);
            scroller.setmDuration(300);
        } catch (Exception e) {
            Log.e("@", "", e);
        }
    }


    public void setReadEffect() {
        setPageTransformer(true, new PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            @Override
            public void transformPage(View view, float position) {

                int pageWidth = view.getWidth();
                int pageHeight =view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);


                } else if (position <= 1) { // (0,1]

                    // Fade the page out.
//                    view.setAlpha(1 - position);
//
//                    // Counteract the default slide transition
                    view.setAlpha(1);
                    view.setTranslationX(pageWidth * -position);
//
//                    // Scale the page down (between MIN_SCALE and 1)
//                    float scaleFactor = MIN_SCALE
//                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
//                    view.setScaleX(scaleFactor);
//                    view.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }


            }
        });
    }
}
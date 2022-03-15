package com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xueyiche.zjyk.xueyiche.R;

/**
 * Created by Muthuramakrishnan on 21-05-2015.
 * www.cepheuen.com
 */

/**
 * Draws circles (one for each view). Views till the position is filled and
 * others are only stroked.
 */
public class ProgressPageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private float mRadius;
    private float mDotGap = 10;
    private int mCurrentPage, mMaxPage;
    private int mFillColor = Color.parseColor("#ffffff");

    private ShapeDrawable mCircleDrawable;
    private ShapeDrawable mStrokeDrawable;
    private LoopViewPager mViewPager;

    public ProgressPageIndicator(Context context) {
        this(context, null);
    }

    public ProgressPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressPageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressPageIndicator, defStyle, 0);

        mRadius = typedArray.getDimension(R.styleable.ProgressPageIndicator_radius, 20);
        mDotGap = typedArray.getDimension(R.styleable.ProgressPageIndicator_dotGap, 10);

        mFillColor = typedArray.getColor(R.styleable.ProgressPageIndicator_fillColor, Color.parseColor("#ffffff"));

        typedArray.recycle();

        drawIndicators();
    }


    /**
     * Sets the radius of the dot indicator
     *
     * @param radius Radius of the dot indicator
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
        invalidate();
    }

    /**
     * Sets the radius of the stroke
     *
     * @param size
     */

    /**
     * Sets the gap between the indicators
     *
     * @param dotGap Gap between the indicators
     */
    public void setDotGap(int dotGap) {
        this.mDotGap = dotGap;
        invalidate();
    }

    /**
     * Sets the fill color of the indicator
     *
     * @param fillColor Fill color of the indicator
     */
    public void setFillColor(int fillColor) {
        this.mFillColor = fillColor;
        invalidate();
    }

    /**
     * Sets the stroke color of the unfilled indicator
     *
     * @param strokeColor Stroke color of the unfilled indicator
     */

    /**
     * Sets the viewpager to the indicator
     *
     * @param viewPager Viewpager
     */
    public void setViewPager(LoopViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(this);
        this.mMaxPage = mViewPager.getAdapter().getCount();
        initialize();
    }

    /**
     * Sets the viewpager to the indicator and points to the specified position
     *
     * @param viewPager       ViewPager
     * @param initialPosition Default position
     */
    public void setViewPager(LoopViewPager viewPager, int initialPosition) {
        setViewPager(viewPager);
        setCurrentPage(initialPosition);
        initialize();
    }

    /**
     * Draws the filled and the stroked indicators
     */
    private void drawIndicators() {
        mCircleDrawable = new ShapeDrawable(new RectShape());
        mCircleDrawable.getPaint().setStyle(Paint.Style.FILL);
        mCircleDrawable.getPaint().setColor(mFillColor);
        mCircleDrawable.getPaint().setAntiAlias(true);
        mCircleDrawable.setIntrinsicHeight(5);
        mCircleDrawable.setIntrinsicWidth((int) mRadius);

        mStrokeDrawable = new ShapeDrawable(new RectShape());
        mStrokeDrawable.getPaint().setStyle(Paint.Style.FILL);
        mStrokeDrawable.getPaint().setColor(Color.parseColor("#999999"));
        mStrokeDrawable.getPaint().setAntiAlias(true);
        mStrokeDrawable.setIntrinsicHeight(5);
        mStrokeDrawable.setIntrinsicWidth((int) mRadius);
    }

    /**
     * Sets the current page
     *
     * @param page Current page
     */
    public void setCurrentPage(int page) {
        this.mCurrentPage = page;
    }

    /**
     * Used to notify the data set change to the indicator
     */
    public void notifyDataSetChanged() {
        this.mViewPager.getAdapter().notifyDataSetChanged();
        mMaxPage = this.mViewPager.getAdapter().getCount();
        initialize();
    }

    private void initialize() {
        prepareDots();
        fillDots();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        drawIndicators();
        initialize();
    }

    /**
     * Preparing the canvas with the indicators
     */
    private void prepareDots() {
//        countDownTimer = new CountDownTimer(3000,1000) {
//            @Override
//            public void onTick(long l) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
//        countDownTimer.start();

        removeAllViews();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int i = width - 48;
        for (int dotLoop = 0; dotLoop < mMaxPage; dotLoop++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(i / mMaxPage, (int) 10.0);
            if (dotLoop != 0) {
                if (getOrientation() == HORIZONTAL)
                    params.setMargins((int) mDotGap, 0, 0, 0);
                else
                    params.setMargins(0, (int) mDotGap, 0, 0);
            }

            imageView.setLayoutParams(params);
            imageView.setImageDrawable(mStrokeDrawable);
            imageView.setId(dotLoop);
            addView(imageView);
        }
    }

    /**
     * Filling the canvas with the indicators
     */
    private void fillDots() {
        for (int fillLoop = 0; fillLoop <= mCurrentPage; fillLoop++) {
            ((ImageView) findViewById(fillLoop)).setImageDrawable(mCircleDrawable.getCurrent());
        }
        for (int unFillLoop = mCurrentPage + 1; unFillLoop < mMaxPage; unFillLoop++) {
            ((ImageView) findViewById(unFillLoop)).setImageDrawable(mStrokeDrawable.getCurrent());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        fillDots();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        initialize();
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }


            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }


        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }
    }
}

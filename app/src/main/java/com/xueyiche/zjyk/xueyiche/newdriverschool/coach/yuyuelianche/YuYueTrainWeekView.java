package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.yuyuelianche;

import android.content.Context;
import android.graphics.Canvas;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;


public class YuYueTrainWeekView extends WeekView {

    private int mRadius;

    public YuYueTrainWeekView(Context context) {
        super(context);
//        //兼容硬件加速无效的代码
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
//        //4.0以上硬件加速会导致无效
//        mSelectedPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
//
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemePaint);
//        mSchemePaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }


    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {
//        int cx = x + mItemWidth / 2;
//        int cy = mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
//        int cx = x + mItemWidth / 2;
//        int top = -mItemHeight / 8;
//        if (isSelected) {
//            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
//                    calendar.isCurrentDay() ? mCurDayTextPaint : mSelectTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10,
//                    calendar.isCurrentDay() ? mCurDayLunarTextPaint : mSelectedLunarTextPaint);
//        } else if (hasScheme) {
//            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
//                    calendar.isCurrentDay() ? mCurDayTextPaint :
//                            calendar.isCurrentMonth() ? mSchemeTextPaint : mSchemeTextPaint);
//
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10, mSchemeLunarTextPaint);
//        } else {
//            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
//                    calendar.isCurrentDay() ? mCurDayTextPaint :
//                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mCurMonthTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10,
//                    calendar.isCurrentDay() ? mCurDayLunarTextPaint : mCurMonthLunarTextPaint);
//        }
        int cx = x + mItemWidth / 2;
        int top =  -mItemHeight / 8;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {

            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    /*calendar.isCurrentDay() ? mCurDayTextPaint :*/ mSelectTextPaint);
            canvas.drawText(calendar.getScheme(), cx, mTextBaseLine  + mItemHeight / 10,
                    /* calendar.isCurrentDay() ? mCurDayLunarTextPaint : */mSelectedLunarTextPaint);
        } /*else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSchemeLunarTextPaint);
        }*/ else {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    mTextBaseLine + top,
                 /*   calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? */

//                 mCurMonthTextPaint

                    isInRange && isEnable ? mCurMonthTextPaint : mOtherMonthTextPaint
                    /*: mOtherMonthTextPaint*/);
            canvas.drawText(calendar.getScheme(),
                    cx,
                    mTextBaseLine + mItemHeight / 10,
                    /*calendar.isCurrentDay() ? mCurDayLunarTextPaint :*/
//                    calendar.getScheme().equals("待设置")||calendar.getScheme().equals("预约") ?
//                            mCurDayLunarTextPaint: mCurMonthLunarTextPaint
                    mCurDayLunarTextPaint

            );
        }
    }
}

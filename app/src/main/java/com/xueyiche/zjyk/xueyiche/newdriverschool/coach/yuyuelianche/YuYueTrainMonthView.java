package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.yuyuelianche;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;


public class YuYueTrainMonthView extends MonthView {

    private int mRadius;

    public YuYueTrainMonthView(Context context) {
        super(context);
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
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);




        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 8;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    /*calendar.isCurrentDay() ? mCurDayTextPaint :*/ mSelectTextPaint);
            canvas.drawText(calendar.getScheme(), cx, mTextBaseLine + y + mItemHeight / 10,
                    /* calendar.isCurrentDay() ? mCurDayLunarTextPaint : */mSelectedLunarTextPaint);

        } /*else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            }
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSchemeLunarTextPaint);
        }*/ else {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    mTextBaseLine + top,
                 /*   calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? */

//                 mCurMonthTextPaint

                    "预约".equals(calendar.getScheme()) ? mCurMonthTextPaint : mOtherMonthTextPaint
                    /*: mOtherMonthTextPaint*/);
            canvas.drawText(TextUtils.isEmpty(calendar.getScheme()) ? "":calendar.getScheme(),
                    cx,
                    mTextBaseLine + y + mItemHeight / 10,
                    /*calendar.isCurrentDay() ? mCurDayLunarTextPaint :*/
                    "预约".equals(calendar.getScheme()) ?
                            mCurDayLunarTextPaint: mCurMonthLunarTextPaint
//                    mCurDayLunarTextPaint

            );
            //mCurDayLunarTextPaint  红色
//            mCurMonthLunarTextPaint   灰色
            //  mSelectTextPaint    白色
            //  mSelectedLunarTextPaint    白色

        }
    }
}

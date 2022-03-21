package com.xueyiche.zjyk.xueyiche.daijia.wheelpicker;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.xueyiche.zjyk.xueyiche.R;


import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.yjf.tata.widget
 * @ClassName: BottomPopTools
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/4/21 8:51
 */
public class BottomPopTools {
    public static void showPop(View v, Activity activity, List<PickerScrollView.PickerList> list, onSelectPickerListener listener) {
        int screenHeigh = activity.getResources().getDisplayMetrics().heightPixels;

        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom)
                .setAnimationStyle(R.style.AnimUp)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.33f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        PickerScrollView addressSelector = view.findViewById(R.id.address);
                        // 设置数据，默认选择第一条
                        addressSelector.setData(list);
                        addressSelector.setSelectedTextColor(0xff5000);
                        addressSelector.setSelected(0);
                        addressSelector.setOnSelectListener(new PickerScrollView.onSelectListener() {
                            @Override
                            public void onSelect(PickerScrollView.PickerList pickers) {

                            }
                        });

                        imageBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                // text.setText(categoryName);addressSelector
                                PickerScrollView.PickerList selectedPicker = addressSelector.getSelectedPicker();

                                listener.onSelect(selectedPicker);
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(activity)
                .showAsBottom(v);
    }

    public static void showPop(View v, Activity activity, int selectedColor, List<PickerScrollView.PickerList> list, onSelectPickerListener listener) {
        int screenHeigh = activity.getResources().getDisplayMetrics().heightPixels;

        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom)
                .setAnimationStyle(R.style.AnimUp)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.33f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        PickerScrollView addressSelector = view.findViewById(R.id.address);
                        // 设置数据，默认选择第一条
                        addressSelector.setData(list);
                        addressSelector.setSelectedTextColor(selectedColor);
                        addressSelector.setSelected(0);
                        addressSelector.setOnSelectListener(new PickerScrollView.onSelectListener() {
                            @Override
                            public void onSelect(PickerScrollView.PickerList pickers) {

                            }
                        });

                        imageBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                                // text.setText(categoryName);addressSelector
                                PickerScrollView.PickerList selectedPicker = addressSelector.getSelectedPicker();

                                listener.onSelect(selectedPicker);
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(activity)
                .showAsBottom(v);
    }

    public static void showPopRound(TextView textView, View v, Activity activity, List<String> list, onClickListener onClickListener) {

    }

    public interface onSelectPickerListener {
        void onSelect(PickerScrollView.PickerList pickers);
    }

    public interface onClickListener {
        void onClick(View view);
    }


}

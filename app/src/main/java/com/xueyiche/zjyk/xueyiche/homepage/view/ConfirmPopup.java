package com.xueyiche.zjyk.xueyiche.homepage.view;

import android.app.Activity;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.DensityUtils;


/**
 * 带确定及取消按钮的弹窗
 *
 */
public abstract class ConfirmPopup<V extends View> extends BasicPopup<View> {
    protected boolean topLineVisible = true;
    protected int topLineColor = 0xFFDDDDDD;
    protected int topBackgroundColor = Color.WHITE;
    protected int topHeight = 40;//dp
    protected boolean cancelVisible = true;
    protected CharSequence cancelText = "";
    protected CharSequence submitText = "";
    protected CharSequence titleText = "";
    protected int cancelTextColor = Color.BLACK;
    protected int submitTextColor = Color.BLACK;
    protected int titleTextColor = Color.BLACK;
    protected int cancelTextSize = 0;
    protected int submitTextSize = 0;
    protected int titleTextSize = 0;

    public ConfirmPopup(Activity activity) {
        super(activity);
        cancelText = activity.getString(android.R.string.cancel);
        submitText = activity.getString(android.R.string.ok);
    }

    /**
     * 设置顶部标题栏下划线颜色
     */
    public void setTopLineColor(@ColorInt int topLineColor) {
        this.topLineColor = topLineColor;
    }

    /**
     * 设置顶部标题栏背景颜色
     */
    public void setTopBackgroundColor(@ColorInt int topBackgroundColor) {
        this.topBackgroundColor = topBackgroundColor;
    }

    /**
     * 设置顶部标题栏高度（单位为dp）
     */
    public void setTopHeight(@IntRange(from = 10, to = 80) int topHeight) {
        this.topHeight = topHeight;
    }

    /**
     * 设置顶部标题栏下划线是否显示
     */
    public void setTopLineVisible(boolean topLineVisible) {
        this.topLineVisible = topLineVisible;
    }

    /**
     * 设置顶部标题栏取消按钮是否显示
     */
    public void setCancelVisible(boolean cancelVisible) {
        this.cancelVisible = cancelVisible;
    }

    /**
     * 设置顶部标题栏取消按钮文字
     */
    public void setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText;
    }

    /**
     * 设置顶部标题栏取消按钮文字
     */
    public void setCancelText(@StringRes int textRes) {
        this.cancelText = activity.getString(textRes);
    }

    /**
     * 设置顶部标题栏确定按钮文字
     */
    public void setSubmitText(CharSequence submitText) {
        this.submitText = submitText;
    }

    /**
     * 设置顶部标题栏确定按钮文字
     */
    public void setSubmitText(@StringRes int textRes) {
        this.submitText = activity.getString(textRes);
    }

    /**
     * 设置顶部标题栏标题文字
     */
    public void setTitleText(CharSequence titleText) {
        this.titleText = titleText;
    }

    /**
     * 设置顶部标题栏标题文字
     */
    public void setTitleText(@StringRes int textRes) {
        this.titleText = activity.getString(textRes);
    }

    /**
     * 设置顶部标题栏取消按钮文字颜色
     */
    public void setCancelTextColor(@ColorInt int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }

    /**
     * 设置顶部标题栏确定按钮文字颜色
     */
    public void setSubmitTextColor(@ColorInt int submitTextColor) {
        this.submitTextColor = submitTextColor;
    }

    /**
     * 设置顶部标题栏标题文字颜色
     */
    public void setTitleTextColor(@ColorInt int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    /**
     * 设置顶部标题栏取消按钮文字大小（单位为sp）
     */
    public void setCancelTextSize(@IntRange(from = 10, to = 40) int cancelTextSize) {
        this.cancelTextSize = cancelTextSize;
    }

    /**
     * 设置顶部标题栏确定按钮文字大小（单位为sp）
     */
    public void setSubmitTextSize(@IntRange(from = 10, to = 40) int submitTextSize) {
        this.submitTextSize = submitTextSize;
    }

    /**
     * 设置顶部标题栏标题文字大小（单位为sp）
     */
    public void setTitleTextSize(@IntRange(from = 10, to = 40) int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    /**
     * @see #makeHeaderView()
     * @see #makeCenterView()
     * @see #makeFooterView()
     */
    @Override
    protected final View makeContentView() {
        LinearLayout rootLayout = new LinearLayout(activity);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setClipToPadding(false);
        View headerView = makeHeaderView();
        if (headerView != null) {

            rootLayout.addView(headerView);
        }
        if (topLineVisible) {
            View lineView = new View(activity);
            lineView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 1));
            lineView.setBackgroundColor(topLineColor);
            rootLayout.addView(lineView);
        }
        View title = makeTitleView();
        if (title!=null) {
            rootLayout.addView(title);
        }
        if (topLineVisible){
            View lineView = new View(activity);
            lineView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 1));
            lineView.setBackgroundColor(topLineColor);
            rootLayout.addView(lineView);
        }

        rootLayout.addView(makeCenterView(), new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1.0f));
        View footerView = makeFooterView();
        if (footerView != null) {
            rootLayout.addView(footerView);
        }
        return rootLayout;
    }

    private View makeTitleView() {
        RelativeLayout topButtonLayout = new RelativeLayout(activity);
        topButtonLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, ConvertUtils.toPx(activity, topHeight)));
        topButtonLayout.setBackgroundColor(topBackgroundColor);
        topButtonLayout.setGravity(Gravity.CENTER_VERTICAL);

        Button cancelButton = new Button(activity);
        cancelButton.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        RelativeLayout.LayoutParams cancelButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        cancelButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        cancelButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        cancelButton.setLayoutParams(cancelButtonLayoutParams);
        cancelButtonLayoutParams.leftMargin = (int) DensityUtils.px2dp(App.context,110);
        cancelButton.setBackgroundColor(Color.TRANSPARENT);
        cancelButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(cancelText)) {
            if (titleText.equals("请选择下证时间")) {
                cancelButton.setText("年");
            }else if (titleText.equals("请选择练车时间")){
                cancelButton.setText("日期");
            }else if (titleText.equals("请选择车辆级别")){
                cancelButton.setText("类型");
            }else if (titleText.equals("请选择预约时间")){
                cancelButton.setText("日期");
            }


        }
        cancelButton.setTextColor(getContext().getResources().getColor(R.color._9999));
        if (cancelTextSize != 0) {
            cancelButton.setTextSize(cancelTextSize);
        }

        topButtonLayout.addView(cancelButton);

        TextView titleView = new TextView(activity);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        int margin = ConvertUtils.toPx(activity, 20);
        titleLayoutParams.leftMargin = margin;
        titleLayoutParams.rightMargin = margin;
        titleLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        titleView.setLayoutParams(titleLayoutParams);
        titleView.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(titleText)) {
            if (titleText.equals("请选择下证时间")) {
                titleView.setText("月");
            }else if (titleText.equals("请选择练车时间")){
                titleView.setText("开始");
            }else if (titleText.equals("请选择预约时间")){
                titleView.setText("时");
            }else if (titleText.equals("请选择车辆级别")){
                titleView.setText("车型");
            }



        }
        titleView.setTextColor(getContext().getResources().getColor(R.color._9999));
        if (titleTextSize != 0) {
            titleView.setTextSize(14);
        }
        topButtonLayout.addView(titleView);

        Button submitButton = new Button(activity);
        RelativeLayout.LayoutParams submitButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        submitButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        submitButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        submitButton.setLayoutParams(submitButtonLayoutParams);
        submitButton.setBackgroundColor(Color.TRANSPARENT);
        submitButtonLayoutParams.rightMargin = (int) DensityUtils.px2dp(App.context,140);
        submitButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(submitText)) {
            if (titleText.equals("请选择下证时间")) {
                submitButton.setText("日");
            }else if (titleText.equals("请选择练车时间")){
                submitButton.setText("结束");
            }else if (titleText.equals("请选择预约时间")){
                submitButton.setText("分");
            }else if (titleText.equals("请选择车辆级别")){
                submitButton.setText("单价");
            }

        }
        submitButton.setTextColor(getContext().getResources().getColor(R.color._9999));
        if (submitTextSize != 0) {
            submitButton.setTextSize(submitTextSize);
        }

        topButtonLayout.addView(submitButton);

        return topButtonLayout;
    }

    @Nullable
    protected View makeHeaderView() {
        RelativeLayout topButtonLayout = new RelativeLayout(activity);
        topButtonLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, ConvertUtils.toPx(activity, topHeight)));
        topButtonLayout.setBackgroundColor(topBackgroundColor);
        topButtonLayout.setGravity(Gravity.CENTER_VERTICAL);

        Button cancelButton = new Button(activity);
        cancelButton.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        RelativeLayout.LayoutParams cancelButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        cancelButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        cancelButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        cancelButton.setLayoutParams(cancelButtonLayoutParams);
        cancelButton.setBackgroundColor(Color.TRANSPARENT);
        cancelButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(cancelText)) {
            cancelButton.setText(cancelText);
        }
        cancelButton.setTextColor(cancelTextColor);
        if (cancelTextSize != 0) {
            cancelButton.setTextSize(cancelTextSize);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onCancel();
            }
        });
        topButtonLayout.addView(cancelButton);

        TextView titleView = new TextView(activity);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        int margin = ConvertUtils.toPx(activity, 20);
        titleLayoutParams.leftMargin = margin;
        titleLayoutParams.rightMargin = margin;
        titleLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        titleView.setLayoutParams(titleLayoutParams);
        titleView.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(titleText)) {
            titleView.setText(titleText);
        }
        titleView.setTextColor(titleTextColor);
        if (titleTextSize != 0) {
            titleView.setTextSize(titleTextSize);
        }
        topButtonLayout.addView(titleView);

        Button submitButton = new Button(activity);
        RelativeLayout.LayoutParams submitButtonLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        submitButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        submitButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        submitButton.setLayoutParams(submitButtonLayoutParams);
        submitButton.setBackgroundColor(Color.TRANSPARENT);
        submitButton.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(submitText)) {
            submitButton.setText(submitText);
        }
        submitButton.setTextColor(submitTextColor);
        if (submitTextSize != 0) {
            submitButton.setTextSize(submitTextSize);
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dismiss();
                onSubmit();
            }
        });
        topButtonLayout.addView(submitButton);

        return topButtonLayout;
    }

    @NonNull
    protected abstract V makeCenterView();

    @Nullable
    protected View makeFooterView() {
        return null;
    }

    protected void onSubmit() {

    }

    protected void onCancel() {

    }

}

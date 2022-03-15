package com.xueyiche.zjyk.xueyiche.mine.view;

import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;


public class CountDownTimerUtils extends CountDownTimer {
    private TextView  mTextView;

    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //设置不可点击
        mTextView.setClickable(false);
        mTextView.setEnabled(false);
        //设置倒计时时间
        mTextView.setText(millisUntilFinished / 1000 + "秒后重发");
        //设置按钮为灰色，这时是不能点击的
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_press);
        //获取按钮上的文字
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        //设置复合文本颜色为红色
        ForegroundColorSpan span = new ForegroundColorSpan(App.context.getResources().getColor(R.color._cccccc));

        //将倒计时的时间设置为红色
        int length = mTextView.getText().length();
        if (length==6) {
            spannableString.setSpan(span, 0, 6, Spannable.SPAN_MARK_MARK);
        }else if (length==5){
            spannableString.setSpan(span, 0, 5, Spannable.SPAN_MARK_MARK);
        }

        mTextView.setText(spannableString);

    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        //重新获得点击
        mTextView.setClickable(true);
        mTextView.setEnabled(true);
        //还原背景色
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);
    }


}

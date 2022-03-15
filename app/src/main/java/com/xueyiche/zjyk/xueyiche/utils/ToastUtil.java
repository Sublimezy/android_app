package com.xueyiche.zjyk.xueyiche.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;


public class ToastUtil extends Toast {

    private Context mContext;
    private Toast mToast = null;

    public ToastUtil(Context context) {
        super(context);
        this.mContext = context;
    }

    public void showToastTop(String text) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        View toastView = LayoutInflater.from(mContext).inflate(R.layout.layout_toast, null);
        if (mToast == null) {
            mToast = new Toast(mContext);
        }
        LinearLayout relativeLayout = (LinearLayout) toastView.findViewById(R.id.test);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wm
                .getDefaultDisplay().getWidth(), dip2px(mContext, 60));
        relativeLayout.setLayoutParams(layoutParams);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.setView(toastView);
        mToast.show();

//        TextView textView = (TextView) toastView.findViewById(R.id.txtToastMessage);
//        textView.setText(text);
    }

    public  int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
